package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {

  public static int distError=0; // Error (amount to close or too far in meters
  public static final int FWDSPEED = 100; // Default rotational speed of wheels
  public static final int BAND_CENTER2 = 25;
  
  public PController() {
    LEFT_MOTOR.setSpeed(FWDSPEED); // Initialize motor rolling forward
    RIGHT_MOTOR.setSpeed(FWDSPEED);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {
    filter(distance);
    int deltaspeed = 5;
    
    distError=distance - BAND_CENTER2; // Compute error
    
    if(BAND_WIDTH > Math.abs(distError)) {
      LEFT_MOTOR.setSpeed(FWDSPEED); // Start moving forward
      RIGHT_MOTOR.setSpeed(FWDSPEED);
      System.out.println("Striaght " + distance );
    }
    
    else if (distError > 0) { //Too far from the wall, change wheel speeds based on magnitude of error
    LEFT_MOTOR.setSpeed(FWDSPEED+(deltaspeed*distError));
    RIGHT_MOTOR.setSpeed(FWDSPEED-(deltaspeed*distError));
    System.out.println("Right " + distance);
    System.out.println(distError);
    }
    
    else if (distError < 0) { // Too close to the wall, change wheel speeds based on magnitude of error
      LEFT_MOTOR.setSpeed(FWDSPEED-(deltaspeed*distError));
      RIGHT_MOTOR.setSpeed(FWDSPEED+(deltaspeed*distError));
      System.out.println("Left " + distance );
      System.out.println(distError);
      }
  }


  @Override
  public int readUSDistance() {
    return this.distance;
  }

}
