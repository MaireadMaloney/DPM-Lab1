package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {

  public static int distError=0; // Error (amount to close or too far in meters
  public static final int FWDSPEED = 100; // Default rotational speed of wheels
  public static final int BAND = 45;
  public static final int BAND_W = 5;
  
  
  public PController() {
    LEFT_MOTOR.setSpeed(FWDSPEED); // Initialize motor rolling forward
    RIGHT_MOTOR.setSpeed(FWDSPEED);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {
    filter(distance);
    int deltaSpeedSlow = 2;
    int deltaSpeedFast = 4;
    distError=distance - BAND; // Compute error
    int speedChangeSlow = Math.abs(deltaSpeedSlow * distError);
    int speedChangeFast = Math.abs(deltaSpeedFast * distError);
    
    if(BAND_W >= Math.abs(distError)) {
      LEFT_MOTOR.setSpeed(FWDSPEED);// Start moving forward
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.setSpeed(FWDSPEED);
      System.out.println("Striaght " + distance );
    }
    
    else if (distError > 0) { //Too far from the wall, change wheel speeds based on magnitude of error
      if(distError > 140) {
        LEFT_MOTOR.setSpeed(50);
        RIGHT_MOTOR.setSpeed(50);
        RIGHT_MOTOR.backward();
      }
    RIGHT_MOTOR.forward();
    LEFT_MOTOR.setSpeed(FWDSPEED+(speedChangeSlow + 30));
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.setSpeed(FWDSPEED-(speedChangeFast-30));
    System.out.println("Right " + distance);
    System.out.println(distError);
    }
    
    else if (distError < 0) { // Too close to the wall, change wheel speeds based on magnitude of error
      if(distError < -25) {
        RIGHT_MOTOR.setSpeed(50);
        LEFT_MOTOR.setSpeed(50);
        LEFT_MOTOR.backward();
      }
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.setSpeed(FWDSPEED+(speedChangeFast));
      LEFT_MOTOR.setSpeed(FWDSPEED-(speedChangeSlow));
      System.out.println("Left " + distance );
      System.out.println(distError);
      }
  }


  @Override
  public int readUSDistance() {
    return this.distance;
  }

}