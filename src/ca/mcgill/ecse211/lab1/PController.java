package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {

  public static int distError=0; // Error (amount to close or too far in meters
  public static final int DEADBAND = 2; // Error threshold
  public static final int FWDSPEED = 150; // Default rotational speed of wheels
  public static final int SLEEPINT = 50; // Sleep interval 50 mS = 20Hz
  
  public PController() {
    LEFT_MOTOR.setSpeed(FWDSPEED); // Initialize motor rolling forward
    RIGHT_MOTOR.setSpeed(FWDSPEED);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {
    filter(distance);
    int deltaspeed = 2;
    
    distError=distance - BAND_CENTER; // Compute error
    
    if(BAND_WIDTH > Math.abs(distError)) {
      LEFT_MOTOR.setSpeed(FWDSPEED); // Start moving forward
      RIGHT_MOTOR.setSpeed(FWDSPEED);
    }
    
    else if (distError < 0) { //Too far from the wall
    LEFT_MOTOR.setSpeed(FWDSPEED+(deltaspeed*distError));
    RIGHT_MOTOR.setSpeed(FWDSPEED-(deltaspeed*distError));
    //LEFT_MOTOR.forward();
    //RIGHT_MOTOR.forward();
    }
    
    else if (distError > 0) { // Too close from the wall
      LEFT_MOTOR.setSpeed(FWDSPEED+(deltaspeed*distError));
      RIGHT_MOTOR.setSpeed(FWDSPEED-(deltaspeed*distError));
      //LEFT_MOTOR.forward();
      //RIGHT_MOTOR.forward();
      }


    // TODO: process a movement based on the us distance passed in (P style)
  }


  @Override
  public int readUSDistance() {
    return this.distance;
  }

}
