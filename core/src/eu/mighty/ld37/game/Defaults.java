package eu.mighty.ld37.game;


public final class Defaults {
	public final static float MAX_SPEED_X = 400;
	public final static float MAX_SPEED_Y = 150;
	public final static float MAX_ACCEL_X = 200;
	public final static float MAX_FRICTION_X = 100;
	public final static float MIN_VEL = 1;

	public final static float PLAYER_ROTATION_HEADING_LEFT = (float) Math.PI / 2;
	public final static float PLAYER_ROTATION_HEADING_RIGHT = (float) Math.PI / -2;
	public final static float PLAYER_WIDTH = (float) 32;

	public final static String playerTextureFile = "moteGraphics/cyanTeamShip01.png";

	public final static String cyanShip1TextureFile = "moteGraphics/cyanTeamShip01.png";
	public final static String cyanShip2TextureFile = "moteGraphics/cyanTeamShip02.png";
	public final static String cyanShip3TextureFile = "moteGraphics/cyanTeamShip03.png";
	public final static String cyanShip4TextureFile = "moteGraphics/cyanTeamShip04.png";
	public final static String cyanGoalShipTextureFile = "moteGraphics/cyanTeamShipGoal.png";

	public final static String orangeShip1TextureFile = "moteGraphics/orangeTeamShip01.png";
	public final static String orangeShip2TextureFile = "moteGraphics/orangeTeamShip02.png";
	public final static String orangeShip3TextureFile = "moteGraphics/orangeTeamShip03.png";
	public final static String orangeShip4TextureFile = "moteGraphics/orangeTeamShip04.png";
	public final static String orangeGoalShipTextureFile = "moteGraphics/orangeTeamShipGoal.png";

	public final static String missileBasicTextureFile = "misils/basic.png";
	public final static int missileBasicSpeed = 500;

	public final static String womanBackgroundTextureFile = "backgrounds/womanLayer.png";
	public final static String woman2BackgroundTextureFile = "backgrounds/woman2Layer.png";
	public final static String manBackgroundTextureFile = "backgrounds/manLayer.png";
	public final static String spiderBackgroundTextureFile = "backgrounds/spiderLayer.png";
	public final static String deepestBackgroundTextureFile = "backgrounds/background.png";

	public final static String windowTitle = "Naves test I";
	public final static int windowWidth = 640;
	public final static int windowHeight = 480;

	public final static int mapWidth = 640 * 5;
	public final static int mapHeight = 480 * 3;
	
	public final static int NUM_HEIGHT_REGIONS = 30;
	public final static int NUM_WIDTH_REGIONS = 50;
	public final static int NUM_WIDTH_ZONES = 5;
	public final static int NUM_HEIGHT_ZONES = 3;

	public final static float CAMERA_CENTER_SHIFT_X = 640 / 4;
	public final static float CAMERA_CENTER_SHIFT_X_MAX_SPEED = 640 / 8;
	public final static float CAMERA_VELOCITY_X = 250;

	public static final int TYPE_WEAPON_BASIC_MISSILE = 0;
	public static final float TIME_TO_REARM_WEAPON_BASIC_MISSILE_SEC = 0.2f;
	public static final float TIME_OF_LIFE_BASIC_MISSILE_SEC = 1f;

	public static final int NUMBER_OF_MEMBERS_IN_ONE_TEAM = 7;
	public static final int NO_TEAM = 0;
	public static final int FRIEND_TEAM = 1;
	public static final int ENEMY_TEAM = 2;

	public static final float MINI_MAP_REDUCTION_FACTOR = 20;

}
