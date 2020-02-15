package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.SparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Shooter {
    private TalonFX _talon0;
    private Joystick joy1;
    public CANEncoder m_encoder;
    public CANSparkMax CSM1;
    private TalonFX _talon1;
    public CANSparkMax CSM0;

    public Shooter(int sht) {
   
    joy1 = new Joystick(0);
    CSM0 = new CANSparkMax(2,CANSparkMaxLowLevel.MotorType.kBrushless);
    CSM0.restoreFactoryDefaults();
    CSM1 = new CANSparkMax(2,CANSparkMaxLowLevel.MotorType.kBrushless);
    CSM1.restoreFactoryDefaults();
  


   
    
    }

    public void shootit() {
        CSM0.set(5);
        CSM1.set(5);
        
    }
}
