package frc.robot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

public class ir{
    //public double ai;
    private DigitalInput ai;
    private DigitalInput ai1;
    private DigitalInput ai2;
    private DigitalInput ai3;
    private DigitalInput ai4;
    

    public ir(int sense){
        ai = new DigitalInput(0);
        ai1 = new DigitalInput(1);
        ai2 = new DigitalInput(2);
        ai3 = new DigitalInput(3);
        ai4 = new DigitalInput(4);
        
        

    }
    public void sendtome() {
        SmartDashboard.putBoolean("ai",ai.get() );
        SmartDashboard.putBoolean("ai1",ai1.get() );
        SmartDashboard.putBoolean("ai2",ai2.get() );
        SmartDashboard.putBoolean("ai3",ai3.get() );
        SmartDashboard.putBoolean("ai4",ai4.get() );
        
    }
}




