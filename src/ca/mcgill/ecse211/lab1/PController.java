package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {

  public static int distError=0; // Error (amount to close or too far in meters
  public static final int FWDSPEED = 200; // Default rotational speed of wheels
  public static final int BAND = 35;
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
    int deltaSpeedFast = 3;
    distError=distance - BAND; // Compute error
    int speedChangeSlow = Math.abs(deltaSpeedSlow * distError);
    int speedChangeFast = Math.abs(deltaSpeedFast * distError);
    
    if (distError == 2147483647) { //handles bad reading
      distError = -25;
    }
    if(BAND_W >= Math.abs(distError)) {
      LEFT_MOTOR.setSpeed(FWDSPEED);// Start moving forward
      RIGHT_MOTOR.setSpeed(FWDSPEED);
      RIGHT_MOTOR.forward();
      LEFT_MOTOR.forward();
      System.out.println("Striaght " + distance );
    }
    else if (distError > 0) { //Too far from the wall, change wheel speeds based on magnitude of error
      if (distError > 70) { //Putting cap on speed change
        distError = 70;
        speedChangeSlow = deltaSpeedSlow * distError;
        speedChangeFast = deltaSpeedFast * distError;
      }
    LEFT_MOTOR.setSpeed(FWDSPEED+(speedChangeSlow));
    RIGHT_MOTOR.setSpeed(FWDSPEED-(speedChangeFast));
    System.out.println("Right " + distance);
    System.out.println(distError);
    System.out.println(FWDSPEED+speedChangeSlow);
    System.out.println(FWDSPEED-speedChangeFast);
    }
    else if (distError < 0) { // Too close to the wall, change wheel speeds based on magnitude of error
      RIGHT_MOTOR.setSpeed(FWDSPEED+(speedChangeFast));
      LEFT_MOTOR.setSpeed(FWDSPEED-(speedChangeFast));
      System.out.println("Left " + distance );
      System.out.println(distError);
      System.out.println(FWDSPEED+speedChangeFast);
      System.out.println(FWDSPEED-speedChangeFast);
      }
      }
  }


  @Override
  public int readUSDistance() {
    return this.distance;
  }

}