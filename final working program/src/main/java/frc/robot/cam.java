package frc.robot;

import edu.wpi.first.networktables.*;


public class cam {

    private final double Kp = -0.1f;
    private final double min_command = 0.05f;
    
    public double steering_adjust;
    public double left_command;
    public double right_command;
    

     // float Kp = -0.1f;
   // float min_command = 0.05f;

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    double tx = table.getEntry("tx").getDouble(0);
    

    public void aimline(){
   
     double heading_error = -tx;
    double steering_adjust = 0.0f;
    if (tx > 1.0)
    {
            steering_adjust = Kp*heading_error - min_command;
            
    }
    else if (tx < 1.0)
    {
            steering_adjust = Kp*heading_error + min_command;
    }
    left_command += steering_adjust;
    right_command -= steering_adjust;

}

}
    

        

    