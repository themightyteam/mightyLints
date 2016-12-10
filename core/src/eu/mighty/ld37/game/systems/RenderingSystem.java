package eu.mighty.ld37.game.systems;

import java.util.Comparator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.components.*;


public class RenderingSystem extends IteratingSystem {
    static final float FRUSTUM_WIDTH = Gdx.graphics.getWidth();
    static final float FRUSTUM_HEIGHT = Gdx.graphics.getHeight();
    static FPSLogger fpsLogger;

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private Comparator<Entity> comparator;
    private OrthographicCamera cam;
    private float camPosX;
    private boolean camPosXLocked;
    private float lastRotation;

    private ComponentMapper<TextureComponent> textureM;
    private ComponentMapper<TransformComponent> transformM;
    private ComponentMapper<PlayerComponent> playerM;
    private ComponentMapper<ExhaustComponent> exhaustM;
    private ComponentMapper<ExplosionComponent> explosionM;
    private ComponentMapper<MovementComponent> movementM;

    public RenderingSystem(SpriteBatch batch) {
        super(Family.all(TransformComponent.class, TextureComponent.class)
                .get());

        textureM = ComponentMapper.getFor(TextureComponent.class);
        transformM = ComponentMapper.getFor(TransformComponent.class);
        playerM = ComponentMapper.getFor(PlayerComponent.class);
        exhaustM = ComponentMapper.getFor(ExhaustComponent.class);
        explosionM = ComponentMapper.getFor(ExplosionComponent.class);
        movementM = ComponentMapper.getFor(MovementComponent.class);

        renderQueue = new Array<Entity>();

        comparator = new Comparator<Entity>() {
            @Override
            public int compare(Entity entityA, Entity entityB) {
                return (int) Math.signum(transformM.get(entityB).pos.z
                        - transformM.get(entityA).pos.z);
            }
        };

        this.batch = batch;

        this.cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);

        camPosX = FRUSTUM_WIDTH / 2 + Defaults.CAMERA_CENTER_SHIFT_X;
        this.cam.position.set(camPosX, FRUSTUM_HEIGHT / 2, 0);

        camPosXLocked = true;
        lastRotation = Defaults.PLAYER_ROTATION_HEADING_RIGHT;

        fpsLogger = new FPSLogger();



    }

    @Override
    public void update(float deltaTime) {
        // System.out.println("Entering RenderingSystem's update");
        super.update(deltaTime);

        renderQueue.sort(comparator);

        for (Entity entity : renderQueue) {
            PlayerComponent pl = playerM.get(entity);

            if (pl == null) {
                continue;
            }

            TransformComponent t = transformM.get(entity);
            this.cam.position.set(
                    calculateCamX(t.pos.x, t.rotation, deltaTime), calculateCamY(t.pos.y),
                    0);

            break;
        }


        this.cam.update();
        batch.setProjectionMatrix(this.cam.combined);
        batch.begin();

        for (Entity entity : renderQueue) {
            TextureComponent tex = textureM.get(entity);

            if (tex.region == null) {
                continue;
            }

            TransformComponent t = transformM.get(entity);

            float width = tex.region.getRegionWidth();
            float height = tex.region.getRegionHeight();
            float originX = width * 0.5f;
            float originY = height * 0.5f;

            batch.draw(tex.region, t.pos.x - originX, t.pos.y - originY,
                    originX, originY, width, height, t.scale.x, t.scale.y,
                    MathUtils.radiansToDegrees * t.rotation);
			// We make the big texture wrap around effect by drawing the same
			// texture "one screen" on the left and on the right. It can be done
			// more efficient by calculating before which textures will be seen
			// in the screen.
			batch.draw(tex.region, t.pos.x - originX - Defaults.mapWidth,
					t.pos.y - originY, originX, originY, width, height,
					t.scale.x, t.scale.y, MathUtils.radiansToDegrees
							* t.rotation);
			batch.draw(tex.region, t.pos.x - originX + Defaults.mapWidth,
					t.pos.y - originY, originX, originY, width, height,
					t.scale.x, t.scale.y, MathUtils.radiansToDegrees
							* t.rotation);

            ExhaustComponent exhaust = exhaustM.get(entity);
            if (exhaust == null) continue;
            //System.out.println("Entering exhaust component");
            MovementComponent movement = movementM.get(entity);
            //if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (shouldDrawExhaust(movement.accel.x, movement.velocity.x)){
                if (t.rotation == Defaults.PLAYER_ROTATION_HEADING_LEFT) {
                    exhaust.pe_left.getEmitters().first().setPosition(t.pos.x + Defaults.PLAYER_WIDTH, t.pos.y);
                    exhaust.pe_right.getEmitters().first().setPosition(t.pos.x, t.pos.y + Defaults.windowHeight*4);
                } else {
                    exhaust.pe_left.getEmitters().first().setPosition(t.pos.x, t.pos.y + Defaults.windowHeight*4);
                    exhaust.pe_right.getEmitters().first().setPosition(t.pos.x - Defaults.PLAYER_WIDTH, t.pos.y);
                }
            } else {
                exhaust.pe_left.getEmitters().first().setPosition(t.pos.x, t.pos.y + Defaults.windowHeight*4);
                exhaust.pe_right.getEmitters().first().setPosition(t.pos.x, t.pos.y + Defaults.windowHeight*4);
            }

            exhaust.pe_left.update(deltaTime);
            exhaust.pe_right.update(deltaTime);

            if (exhaust.pe_left.isComplete()) {
                exhaust.pe_left.reset();
            }
            if (exhaust.pe_right.isComplete()) {
                exhaust.pe_right.reset();
            }

            exhaust.pe_left.draw(batch);
            exhaust.pe_right.draw(batch);

            ExplosionComponent explosion = explosionM.get(entity);
            if (explosion == null) continue;
            if (Gdx.input.isKeyPressed(Keys.X)) {
                System.out.println("Set explosion to true");
                explosion.destroyed = true;
            }
            explosion.pe_explosion.update(deltaTime);
            if (explosion.destroyed) {
                explosion.pe_explosion.getEmitters().first().setPosition(t.pos.x, t.pos.y);
                explosion.destroyed = false;
                explosion.pe_explosion.reset();
            }

            explosion.pe_explosion.draw(batch);

        }




        batch.end();
        renderQueue.clear();

        fpsLogger.log();

    }

    private boolean shouldDrawExhaust(float accel, float velocity) {
        return (!(((accel > 0)&&(velocity < 0))||((accel < 0)&&(velocity > 0)))
                &&!(velocity == 0.0f)
                ||(Math.abs(accel) > Defaults.MAX_FRICTION_X));
    }

    private float calculateCamY(float y) {
        if (y > FRUSTUM_HEIGHT) return (float)FRUSTUM_HEIGHT;
        if (y < 0) return 0;
        return y;
    }

    private float calculateCamX(float x, float rotation, float deltaTime) {
		// In case of a wrap around of the ship, the camera is on the other part
		// of the map, this has to be fixed before updating.
		if (Math.abs(x - this.camPosX) >= Defaults.mapWidth) {
			if (x < Defaults.mapWidth / 2) {
				this.camPosX = this.camPosX - Defaults.mapWidth;
			} else {
				this.camPosX = this.camPosX + Defaults.mapWidth;
			}
		}

        if (rotation == Defaults.PLAYER_ROTATION_HEADING_RIGHT) {
            // Update if the camera is already locked
            if (lastRotation == Defaults.PLAYER_ROTATION_HEADING_LEFT) {
                camPosXLocked = false;
            } else if (camPosXLocked
                    | this.camPosX > x
                    + Defaults.CAMERA_CENTER_SHIFT_X_MAX_SPEED) {
                camPosXLocked = true;
            } else {
                camPosXLocked = false;
            }
            lastRotation = Defaults.PLAYER_ROTATION_HEADING_RIGHT;

            // Apply a velocity to the camera if necessary, keep it between
            // the borders.
            if (this.camPosX <= x + Defaults.CAMERA_CENTER_SHIFT_X) {
                this.camPosX = this.camPosX + Defaults.CAMERA_VELOCITY_X
                        * deltaTime;
                if (this.camPosX >= x + Defaults.CAMERA_CENTER_SHIFT_X) {
                    this.camPosX = x + Defaults.CAMERA_CENTER_SHIFT_X;
                }
                if (camPosXLocked
                        & this.camPosX <= x
                        + Defaults.CAMERA_CENTER_SHIFT_X_MAX_SPEED) {
                    this.camPosX = x + Defaults.CAMERA_CENTER_SHIFT_X_MAX_SPEED;
                }
            } else {
                this.camPosX = x + Defaults.CAMERA_CENTER_SHIFT_X;
            }
        } else {
            // Update if the camera is already locked
            if (lastRotation == Defaults.PLAYER_ROTATION_HEADING_RIGHT) {
                camPosXLocked = false;
            } else if (camPosXLocked
                    | this.camPosX < x
                    - Defaults.CAMERA_CENTER_SHIFT_X_MAX_SPEED) {
                camPosXLocked = true;
            } else {
                camPosXLocked = false;
            }
            lastRotation = Defaults.PLAYER_ROTATION_HEADING_LEFT;

            if (this.camPosX >= x - Defaults.CAMERA_CENTER_SHIFT_X) {
                this.camPosX = this.camPosX - Defaults.CAMERA_VELOCITY_X
                        * deltaTime;
                if (this.camPosX <= x - Defaults.CAMERA_CENTER_SHIFT_X) {
                    this.camPosX = x - Defaults.CAMERA_CENTER_SHIFT_X;
                }
                if (camPosXLocked
                        & this.camPosX >= x
                        - Defaults.CAMERA_CENTER_SHIFT_X_MAX_SPEED) {
                    this.camPosX = x - Defaults.CAMERA_CENTER_SHIFT_X_MAX_SPEED;
                }
            } else {
                this.camPosX = x - Defaults.CAMERA_CENTER_SHIFT_X;
            }
        }
        return this.camPosX;
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        // System.out.println("Entering RenderingSystem's processEntity");
        renderQueue.add(entity);
    }

    public OrthographicCamera getCamera() {
        return cam;
    }
}
