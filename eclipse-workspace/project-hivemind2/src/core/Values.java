package core;

import org.newdawn.slick.Color;



/*  Raider:      SPEED = 45   ACC = 42    RANGE: 400
 *  Assault      SPEED = 28   ACC = 35    RANGE: 385
 *  Specialist:  SPEED = 26   ACC = 20    RANGE: 1050
 *  Support:     SPEED = 38   ACC = 70    RANGE: 450
 *  Miner:       SPEED = 30   ACC = 30    RANGE: 375
 */

public interface Values 
{ 
	public final static int BLUE_ID = 0;
	public final static int RED_ID = 1;
	public final static int NEUTRAL_ID = 2;
	public final static int AMBIENT_ID = 3;
	
	public final static int RESOLUTION_X = 1920; 
	public final static int RESOLUTION_Y = 1080; 
	public final static int TRANSITION_FADE_TIME = 60;
	public final static int TRANSITION_FADE_TIME_SLOW = 240;
	
	// Public Constants
	
	final public static Color[] COLORS = { new Color(6, 160, 224), new Color(225, 25, 30)};
	final public static Color[] COLORS_DARK = { new Color(6, 110, 174), new Color(185, 17, 25)};
	final public static Color[] COLORS_150 = { new Color(6, 160, 224, 150), new Color(225, 25, 30, 150), new Color(225, 25, 225, 150)};
	final public static Color[] COLORS_150_DARK = { new Color(6, 110, 174, 150), new Color(185, 17, 25, 150), new Color(185, 15, 185, 150)};
	final public static Color[] COLORS_35 = { new Color(10, 100, 200, 25), new Color(200, 50, 10, 35), new Color(200, 25, 200, 35)};

	final public static Color BLUE =  new Color(6, 180, 224);
	final public static Color RED = new Color(225, 25, 30);
	final public static Color PURPLE = new Color(225, 25, 225);

	public final static int PLAYFIELD_WIDTH = 28000;//(int)(25600*1); // 27
	public final static int PLAYFIELD_HEIGHT = 23000;//(int)(16000*1.2);

	public final static int CONCEALMENT_RANGE = 300;
	
	public final static int STARFIELD_WIDTH = 28000;//(int)(27200*1);
	public final static int STARFIELD_HEIGHT = 23000; //(int)(17000*1.2);

	public final static int GAME_SPEED = 1;
	public final static int FRAMES_PER_SECOND = 120;
	public final static int STARTING_MINERALS = 30;
	
	public final static int RESEARCH_TIME = FRAMES_PER_SECOND * 50;
	
	public final static float COMBAT_VALUE_UPGRADE_MULTIPLIER = 1.3f;
	
	public final static int LATENCY_YELLOW = 750;
	public final static float LATENCY_YELLOW_MINERAL_EFFICIENCY = .8f;
	
	public final static int LATENCY_RED = 1500;
	public final static float LATENCY_RED_MINERAL_EFFICIENCY = .5f;

	public final static float MUSIC_SECRET_CHANCE = .05f;
	// currently deprecated
	public final static int UPGRADE_COST = 0;
	public final static int UPGRADE_TAX = 0;
	
	
	public final static int ALERT_QUICK = 60;
	public final static int ALERT_STANDARD = 90;
	public final static int ALERT_LONG = 120;

	public final static int ASTROID_BASE_SHIP_PROXIMITY = 2000;
	public final static int ASTROID_BASE_SHIP_PROXIMITY_SPAWN = ASTROID_BASE_SHIP_PROXIMITY + 200;
	public final static int ASTROID_BOUNDARY_PROXIMITY = 1250;
	public final static int ASTROID_BOUNDARY_PROXIMITY_SPAWN = ASTROID_BOUNDARY_PROXIMITY + 100;
	public final static int ASTEROID_MINERALS_BASE = 15;
	public final static float ASTEROID_MIN_SPEED = .03f;
	public final static float ASTEROID_MAX_SPEED = .06f;
	public final static int ASTEROID_MIN_SIZE = 2;
	public final static int ASTEROID_MID_SIZE = 3;
	public final static int ASTEROID_MAX_SIZE = 4;
	public final static int ASTEROID_SPAWN_WIDTH = PLAYFIELD_WIDTH - ASTROID_BOUNDARY_PROXIMITY_SPAWN * 2;
	public final static int ASTEROID_SPAWN_HEIGHT = PLAYFIELD_HEIGHT - ASTROID_BOUNDARY_PROXIMITY_SPAWN * 2;
	public final static float ASTEROID_HIGH_YIELD_MULTIPLIER = 2.00f;
	public final static int ASTEROID_HIGH_YIELD_SPAWN_WIDTH = 5500;
	public final static int ASTEROID_HIGH_YIELD_SPAWN_HEIGHT = PLAYFIELD_HEIGHT - ASTROID_BOUNDARY_PROXIMITY_SPAWN * 2;
	
	
	public final static int NEBULA_SPAWN_WIDTH = PLAYFIELD_WIDTH/8;
	public final static int NEBULA_SPAWN_HEIGHT = PLAYFIELD_HEIGHT/8;
	
	public final static int NEBULA_MIN_SIZE = 3000;
	public final static int NEBULA_MAX_SIZE = 3800;
	
	public final static int STAR_BASE_SIZE = 2000;
	public final static int STAR_LEGENDARY_SIZE = (int) (STAR_BASE_SIZE * 1.5f);
	public final static int STAR_SOLAR_FLARE_DURATION = 200;
	public final static int STAR_SOLAR_FLARE_DAMAGE = 200;
	public final static int STAR_SOLAR_FLARE_RANGE = 3000;
	public final static int STAR_SOLAR_FLARE_COOLDOWN = 5000;

	
	public final static int PULSAR_BASE_SIZE = 800;
	public final static int PULSAR_LEGENDARY_SIZE = (int) (PULSAR_BASE_SIZE * 1.5f);
	public final static int PULSAR_PULSE_DURATION = 200;
	public final static int PULSAR_PULSE_STUN_DURATION = 1000;
	public final static int PULSAR_PULSE_RANGE = 3000;
	public final static int PULSAR_PULSE_COOLDOWN = 5000;
	
	public final static int SHIELD_RECOVERY_DELAY = 480;

	// Special Effects

	// BASE SHIP + TURRET
	
	public final static float TURRET_ATTACK_DAMAGE = 100;
	public final static int TURRET_ATTACK_SPEED = 25;
	public final static float TURRET_ATTACK_RANGE = 600;
	public final static int TURRET_ATTACK_COOLDOWN = 90;

	public final static float ACC = .001f; 		
	public final static float SPEED = .1f; 		

	public final static float BASE_SHIP_X_POSITION = 9000;		
	public final static float BASE_SHIP_Y_POSITION = 0;
	public final static float BASE_SHIP_Y_POSITION_ALTERNATE = 2500;

	public final static float BASE_SHIP_SPEED = 1.3f * SPEED; 
	public final static float BASE_SHIP_DRIFT = .0025f; 

	public final static float BASE_SHIP_MOVE_START = 0;				
	public final static float BASE_SHIP_ARMOR = 50;

	public final static int BASE_ENERGY_MAX = 200;
	public final static float BASE_ENERGY_REGEN = .02f;  // 2.5 energy per second
	
	public final static int BASE_UNIT_VALUE_CAP = 250;
	public final static int BASE_BUILD_SPEED = 40;
	public final static float BASE_UPGRADE_PRODUCTION_MODIFIER = 1f;	  

	public final static int HAZARD_Y_POSITION = -1500;
	public final static int PIRATE_Y_POSITION = -5000;

	
	// Raider
	// - Basic: Single Target, Rapid Fire, Mid Range Weapon
	// - Armor Piercing Rounds: Deals extra damage and ignores the target's armor
	// - Improved Engines: Increases max speed and acceleration
	// - [Active] Missiles: Adds a missile

	public final static int RAIDER_COST = 3;
	public final static int RAIDER_BUILD_TIME = RAIDER_COST * BASE_BUILD_SPEED;

	public final static int RAIDER_HEALTH = 135;
	public final static float RAIDER_SPEED = 45 * SPEED;				
	public final static float RAIDER_ACCELERATION = 42 * ACC;

	public final static float RAIDER_ATTACK_DAMAGE = 50;   
	public final static int RAIDER_ATTACK_SPEED = 20;
	public final static float RAIDER_ATTACK_RANGE = 400;
	public final static int RAIDER_ATTACK_COOLDOWN = 120;	
	public final static float RAIDER_ATTACK_RECOIL = 0; //.3f*8;
	public final static float RAIDER_DODGE_CHANCE = 25;

	// Piercing Shot
	public final static float RAIDER_UPGRADE_PIERCE_DAMAGE = 1.20f;		// Increases damage by this percent
	public final static float RAIDER_UPGRADE_PIERCE_RANGE_BONUS = 0;	// Provides no range bonus
	public final static float RAIDER_UPGRADE_PIERCE_PERCENT = .4f;		// Ignores this percentage of target's armor
	// Engine
	public final static float RAIDER_UPGRADE_ENGINE_SPEED_MULTIPLIER = 1.30f;
	public final static float RAIDER_UPGRADE_ENGINE_ACCELERATION_MULTIPLIER = 1.30f;
	public final static float RAIDER_UPGRADE_ENGINE_DODGE_CHANCE = 50;

	// Missile
	public final static int RAIDER_UPGRADE_MISSILE_CHARGES = 1;
	public final static float RAIDER_UPGRADE_MISSILE_SPEED = 100 * SPEED;
	public final static float RAIDER_UPGRADE_MISSILE_ACCELERATION = 250 * ACC;
	public final static float RAIDER_UPGRADE_MISSILE_RANGE = 1200;
	public final static float RAIDER_UPGRADE_MISSILE_HEALTH = 1;
	public final static float RAIDER_UPGRADE_MISSILE_DAMAGE = 80;
	public final static int RAIDER_UPGRADE_MISSILE_DODGE_CHANCE = 40;
	public final static int RAIDER_UPGRADE_MISSILE_TIMER = 300;
	public final static int RAIDER_UPGRADE_MISSILE_EFFECT_RADIUS = 75;
	public final static int RAIDER_UPGRADE_MISSILE_TRIGGER_RADIUS = 60;
	
	// Miner
	// - No Basic Attack
	// - Hull Upgrade - Armor, Health, and Capacity
	// - Advanced Mining Laser - Weapon and Faster Mine Rate
	// - [Active] SPACEMINES!!!!!!!!!!

	public static int MINER_COST = 5;
	public final static int MINER_BUILD_TIME = MINER_COST * BASE_BUILD_SPEED * 2;

	public final static int MINER_HEALTH = 400;
	public final static float MINER_SPEED = 30 * SPEED;
	public final static float MINER_ACCELERATION = 30 * ACC;
	public final static float MINER_CAPACITY = 10;
	public final static float MINER_RATE = .0022f;
	public final static int MINER_ARMOR = 10;

	// Mining Laser
	public final static float MINER_ATTACK_DAMAGE = 100;
	public final static int MINER_ATTACK_SPEED = 15;
	public final static float MINER_ATTACK_RANGE = 375;
	public final static int MINER_ATTACK_COOLDOWN = 300;
	public final static float MINER_UPGRADE_RATE = MINER_RATE;		

	//  Hull Upgrade
	public final static int MINER_UPGRADE_HULL_ARMOR_BONUS = 20;
	public final static int MINER_UPGRADE_HULL_HEALTH_BONUS = 100;
	public final static int MINER_UPGRADE_HULL_CAPACITY_BONUS = 4;			
	
	public final static int MINER_UPGRADE_MINE_AGGRO_RADIUS = 1000;			
	public final static int MINER_UPGRADE_MINE_DAMAGE_RADIUS = 200;			
	public final static int MINER_UPGRADE_MINE_TRIGGER_RADIUS = 125;			
	public final static int MINER_UPGRADE_MINE_DAMAGE_VALUE = 250;			
	public final static int MINER_UPGRADE_MINE_DROP_COOLDOWN = 120;				
	public final static int MINER_UPGRADE_MINE_DROP_MINERAL_COST = 3;			
	public final static int MINER_UPGRADE_MINE_DROP_REACH = 150;			
	public final static float MINER_UPGRADE_MINE_MAX_SPEED = 2.0f;			
	public final static float MINER_UPGRADE_MINE_MAX_ACCELERATION = .3f;			
	public final static int MINER_UPGRADE_MINE_BUILD_TIME = 240;			

	
	// Assault
	// - Basic: Small AOE, Middle Fire, Mid Range Weapon w/ Recoil
	// - Explosive Barrage: Increase AOE of primary attack
	// - Shield: Adds a defensive shield that regenerates over time
	//           Note:  shields do not benefit from armor
	// - [Active] Aegis - 50% to damage for X seconds

	public final static int ASSAULT_COST = 7;
	public final static int ASSAULT_BUILD_TIME = ASSAULT_COST * BASE_BUILD_SPEED;

	public final static int ASSAULT_HEALTH = 500;
	public final static int ASSAULT_ARMOR = 40;
	public final static float ASSAULT_SPEED = 28 * SPEED;
	public final static float ASSAULT_ACCELERATION = 35 * ACC;
	public final static float ASSAULT_ATTACK_DAMAGE = 85; 
	public final static int ASSAULT_ATTACK_SPEED = 25;
	public final static float ASSAULT_ATTACK_RANGE = 380;
	public final static int ASSAULT_ATTACK_COOLDOWN = 150;
	public final static int ASSAULT_ATTACK_SPLASH_RADIUS = 150;
	public final static float ASSAULT_ATTACK_RECOIL = 0; //1.0f; 
	public final static int ASSAULT_SLOW_DURATION = 250;
	public final static float ASSAULT_SPEED_REDUCTION = .3f;
	
	// Explosive Upgrade
	public final static float ASSAULT_UPGRADE_EXPLOSIVE_SPLASH_RADIUS_MOD = 1.5f;
	public final static int ASSAULT_UPGRADE_EXPLOSIVE_SLOW_DURATION = 500;
	public final static float ASSAULT_UPGRADE_EXPLOSIVE_SPEED_REDUCTION = .6f;
	public final static float ASSAULT_UPGRADE_EXPLOSIVE_RANGE_INCREASE = 0;

	// Shield Upgrade
	public final static float ASSAULT_UPGRADE_ENERGY_START_PERCENT = .25f;
	public final static float ASSAULT_UPGRADE_SHIELD_VALUE = 200;
	public final static float ASSAULT_UPGRADE_SHIELD_REGEN = .2f;
	
	// Aegis Upgrade
	public final static float ASSAULT_UPGRADE_AEGIS_ENERGY_COST = 80;
	public final static int ASSAULT_UPGRADE_AEGIS_DURATION = 500;
	public final static float ASSAULT_UPGRADE_AEGIS_DAMAGE_BLOCKED = .5f;

	// Specialist
	   // - Basic: Long range shot, costs energy to use
	   // - Kinetic Blast - Adds stun and knockback to attack
	   // - Fusion Reactor - Increases max energy and energy regeneration
	   // - [Active] Rift: AOE that pulls targets to middle

	public final static int SPECIALIST_COST = 11;
	public final static int SPECIALIST_BUILD_TIME = SPECIALIST_COST * BASE_BUILD_SPEED;

	public final static int SPECIALIST_HEALTH = 550;
	public final static int SPECIALIST_ARMOR = 0;
	public final static float SPECIALIST_SPEED = 26 * SPEED;
	public final static float SPECIALIST_ACCELERATION = 20 * ACC;
	
	public final static float SPECIALIST_ATTACK_DAMAGE = 360;
	public final static float SPECIALIST_ATTACK_ENERGY_COST = 22;
	public final static float SPECIALIST_ATTACK_RANGE = 1050;
	public final static int SPECIALIST_ATTACK_COOLDOWN = 550;
	public final static float SPECIALIST_ATTACK_RECOIL = 0f;
	public final static int SPECIALIST_ATTACK_LOCK_TIME = 60;

	// Kinetic Beam
	public final static int SPECIALIST_UPGRADE_KINETIC_STUN_TIME = 150;
	public final static float SPECIALIST_UPGRADE_KINETIC_PUSH = 3.5f;
	public final static float SPECIALIST_UPGRADE_KINETIC_DAMAGE_BONUS = 1.2f;

	// Energy Upgrade:  Increases max energy + regen
	public final static float SPECIALIST_UPGRADE_REACTOR_REGEN = .03f; 
	public final static float SPECIALIST_UPGRADE_REACTOR_VALUE = 300; 
	
	// Rift Upgrade	
	public final static int SPECIALIST_RIFT_ENERGY_COST = 100;
	public final static float SPECIALIST_RIFT_RANGE = SPECIALIST_ATTACK_RANGE;
	public final static int SPECIALIST_RIFT_RADIUS = 700;
	public final static int SPECIALIST_RIFT_DURATION = 700;
	public final static float SPECIALIST_ENERGY_START_PERCENT = .1f;
	public final static float SPECIALIST_RIFT_PULL_RATE = .10f;
	
	// Support
	  // - Basic: Short range heal
	   // - Fix: Healing more effective and reduces duration of status effects
	   // - Battery:  Increase max energy and regeneration
	   // - [Active] EMP: AOE Stun Around Support  (enemy only)
	   //   Expends all of its energy; area and duration is based on energy used

	public final static int SUPPORT_COST = 4;
	public final static int SUPPORT_BUILD_TIME = SUPPORT_COST * BASE_BUILD_SPEED;

	public final static int SUPPORT_HEALTH = 360;
	public final static int SUPPORT_ARMOR = 10;
	public final static float SUPPORT_SPEED = 38 * SPEED;
	public final static float SUPPORT_ACCELERATION = 70 * ACC;
	public final static float SUPPORT_HEAL_AMOUNT = 20;

	public final static int SUPPORT_HEAL_SPEED = 25;
	public final static float SUPPORT_HEAL_RANGE = 450;
	public final static int SUPPORT_HEAL_COOLDOWN = 60;
	public final static int SUPPORT_HEAL_ENERGY_COST = 2;			
	public final static float SUPPORT_ENERGY_START_PERCENT = .25f;

    // EMP Upgrade:  Allows use of the EMP Ability
	
	public final static float SUPPORT_UPGRADE_EMP_RADIUS_PER_ENERGY = 2.8f;
	public final static float SUPPORT_UPGRADE_EMP_DURATION_PER_ENERGY = 1.3f;  
	public final static int SUPPORT_UPGRADE_EMP_MINIMUM_ENERGY = 75;	
	public final static int SUPPORT_UPGRADE_EMP_MINIMUM_RADIUS = (int) (SUPPORT_UPGRADE_EMP_MINIMUM_ENERGY * SUPPORT_UPGRADE_EMP_RADIUS_PER_ENERGY);
	public final static int SUPPORT_UPGRADE_EMP_MINIMUM_DURATION = (int) (SUPPORT_UPGRADE_EMP_MINIMUM_ENERGY * SUPPORT_UPGRADE_EMP_DURATION_PER_ENERGY);
	
	// Energy Upgrade:  Increases max energy + regen
	public final static float SUPPORT_UPGRADE_ENERGY_REGEN = .03f; 
	public final static float SUPPORT_UPGRADE_ENERGY_VALUE = 300; 
	
	// Fix Upgrade:  Increases healing and status removal
	public final static int SUPPORT_UPGRADE_FIX_HEAL_AMOUNT = 30;
	public final static int SUPPORT_UPGRADE_FIX_EFFECT_REDUCTION = 60;   

}
