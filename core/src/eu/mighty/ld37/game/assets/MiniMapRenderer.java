package eu.mighty.ld37.game.assets;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

import eu.mighty.ld37.game.Defaults;
import eu.mighty.ld37.game.components.CanScoreComponent;
import eu.mighty.ld37.game.components.GoalComponent;
import eu.mighty.ld37.game.components.PlayerComponent;
import eu.mighty.ld37.game.components.TeamComponent;
import eu.mighty.ld37.game.components.TransformComponent;

public class MiniMapRenderer {
	private float reductionFactor;
	private float miniMapWidth;
	private float miniMapHeight;
	private float miniMapPosX;
	private float miniMapPosY;
	private float mapCenterX;

	private Color backgroundColor;
	private ShapeRenderer shapeRenderer;

	private float MINIMAP_SIZE_NORMAL_SHIP = 2;
	private float MINIMAP_SIZE_GOAL_SHIP = 4;

	private ComponentMapper<PlayerComponent> pm = ComponentMapper
			.getFor(PlayerComponent.class);
	private ComponentMapper<TeamComponent> tem = ComponentMapper
			.getFor(TeamComponent.class);
	private ComponentMapper<TransformComponent> trm = ComponentMapper
			.getFor(TransformComponent.class);
	private ComponentMapper<CanScoreComponent> csm = ComponentMapper
			.getFor(CanScoreComponent.class);
	private ComponentMapper<GoalComponent> gm = ComponentMapper
			.getFor(GoalComponent.class);

	public MiniMapRenderer() {
		reductionFactor = Defaults.MINI_MAP_REDUCTION_FACTOR;
		miniMapWidth = Defaults.mapWidth / reductionFactor;
		miniMapHeight = Defaults.mapHeight / reductionFactor;
		miniMapPosX = Defaults.windowWidth / 2 - miniMapWidth / 2;
		miniMapPosY = Defaults.windowHeight - miniMapHeight;

		this.backgroundColor = new Color(0f, 0f, 0f, .5f);
		this.shapeRenderer = new ShapeRenderer();
	}

	public void render(Array<Entity> renderQueue) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		// Paint map background

		paintRectangle(miniMapPosX - MINIMAP_SIZE_NORMAL_SHIP, miniMapPosY,
				miniMapWidth + MINIMAP_SIZE_NORMAL_SHIP * 2,
				miniMapHeight,
				this.backgroundColor);

		// Get the center of the map (where the player is) and paint the player
		for (Entity entity : renderQueue) {
			PlayerComponent pl = pm.get(entity);

			if (pl == null) {
				continue;
			}

			TransformComponent t = trm.get(entity);
			mapCenterX = t.pos.x;
			
			float size = MINIMAP_SIZE_NORMAL_SHIP;

			GoalComponent g = gm.get(entity);
			if (g != null) {
				size = this.MINIMAP_SIZE_GOAL_SHIP;
			}

			paintShipInMinimap(t.pos.x, t.pos.y, t.pos.x, size, size,
					Color.BLUE);
		}
		
		// Paint the rest of the ships
		for (Entity entity : renderQueue) {
			PlayerComponent pl = pm.get(entity);

			if (pl != null) {
				continue;
			}

			TeamComponent team = tem.get(entity);
			if (team == null) {
				continue;
			}

			TransformComponent t = trm.get(entity);
			CanScoreComponent csc = csm.get(entity);

			Color c = Color.GOLD;
			if (team.team == Defaults.ENEMY_TEAM) {
				c = Color.ORANGE;
				if (csm != null) {
					c = Color.GOLD;
				}
			}
			if (team.team == Defaults.FRIEND_TEAM) {
				c = Color.CYAN;
				if (csm == null) {
					c = Color.NAVY;
				}
			}

			float size = MINIMAP_SIZE_NORMAL_SHIP;

			GoalComponent g = gm.get(entity);
			if (g != null) {
				size = this.MINIMAP_SIZE_GOAL_SHIP;
			}

			paintShipInMinimap(t.pos.x, t.pos.y, mapCenterX, size, size, c);
		}

		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	private void paintShipInMinimap(float mapX, float mapY, float mapCenterX,
			float width, float height, Color color) {
		float transformedX = (((mapX - mapCenterX) + Defaults.mapWidth / 2) % Defaults.mapWidth)
				/ reductionFactor;
		if (transformedX < 0) {
			transformedX += miniMapWidth;
		}
		float transformedY = mapY / reductionFactor;
		paintRectangle(transformedX + miniMapPosX, transformedY + miniMapPosY,
				width, height, color);
	}

	private void paintRectangle(float x, float y, float width, float height,
			Color color) {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(color);
		shapeRenderer.rect(x, y, width, height);
		shapeRenderer.end();
	}
}
