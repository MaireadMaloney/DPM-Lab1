package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {

  public static int distError=0; // Error (amount to close or too far in meters
  public static final int FWDSPEED = 150; // Default rotational speed of wheels
  
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
    int speedChange = Math.abs(deltaspeed * distError);
    
    if(BAND_WIDTH >= Math.abs(distError)) {
      LEFT_MOTOR.setSpeed(FWDSPEED); // Start moving forward
      RIGHT_MOTOR.setSpeed(FWDSPEED);
      System.out.println("Striaght " + distance );
    }
    
    else if (distError > 0) { //Too far from the wall, change wheel speeds based on magnitude of error
    LEFT_MOTOR.setSpeed(FWDSPEED+(speedChange));
    RIGHT_MOTOR.setSpeed(FWDSPEED-(speedChange));
    System.out.println("Right " + distance);
    System.out.println(distError);
    }
    
    else if (distError < 0) { // Too close to the wall, change wheel speeds based on magnitude of error
      LEFT_MOTOR.setSpeed(FWDSPEED-(speedChange));
      RIGHT_MOTOR.setSpeed(FWDSPEED+(speedChange));
      System.out.println("Left " + distance );
      System.out.println(distError);
      }
  }


  @Override
  public int readUSDistance() {
    return this.distance;
  }

}
