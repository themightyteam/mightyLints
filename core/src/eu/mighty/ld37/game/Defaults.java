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


	public final static String playerTextureFile = "starships/1.png";
	public final static String friendTextureFile = "starships/2.png";
	public final static String enemyTextureFile = "starships/3.png";

	public final static String missileBasicTextureFile = "misils/basic.png";
	public final static int missileBasicSpeed = 500;

	public final static String blueBackgroundTextureFile = "backgrounds/blueLayer.png";
	public final static String yellowBackgroundTextureFile = "backgrounds/yellowLayer.png";
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

	public static final int NUMBER_OF_MEMBERS_IN_ONE_TEAM = 3;
	public static final int NO_TEAM = 0;
	public static final int FRIEND_TEAM = 1;
	public static final int ENEMY_TEAM = 2;

}
