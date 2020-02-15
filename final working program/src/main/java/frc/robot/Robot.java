/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private TalonFX _talon0;
  private TalonFX _talon1;
  private Joystick joy1;
  private Joystick joy2;
  private Pnuematics pnue;
  private Turret ct; 
  private cam lm;
  private ir irs;
  private Shooter st;
  private Intake ibl;
  private feedmemoter fmm;
  // public CANSparkMax CSM0;
  // private double boop;
  // private double jax;
  // private double cfa;
  // private double cfb;
  // private double cfc;
  // private boolean hbbbp;
  // private final double enc = 118;
  // private final double timeset = 0.0200;
  // public CANEncoder m_encoder;
  // public final double spd = 0.1;
  // public CANSparkMax CSM1;

  /**
   *
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    _talon0 = new TalonFX(1);
    _talon1 = new TalonFX(2);
    joy1 = new Joystick(0);
    ct = new Turret(4);
    joy2 = new Joystick(1);
    lm = new cam();
    pnue = new Pnuematics();
    irs = new ir(3);
    st = new Shooter(6);
    ibl = new Intake(7);
    // CSM0 = new CANSparkMax(4,CANSparkMaxLowLevel.MotorType.kBrushless);
    // CSM0.restoreFactoryDefaults();
    // //CSM0.getEncoder().getPosition();
    // hbbbp = false;
    // m_encoder = CSM0.getEncoder();
    // CSM1 = new CANSparkMax(1,CANSparkMaxLowLevel.MotorType.kBrushless);

    // NeoMotorWposlog Neo1 = new NeoMotorWposlog(1);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
    case kCustomAuto:
      // Put custom auto code here
      break;
    case kDefaultAuto:
    default:
      // Put default auto code here
      break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopInit() {
    ct.reset();
    _talon0.configIntegratedSensorOffset(0);
    _talon1.configIntegratedSensorOffset(0);
    // CSM1.restoreFactoryDefaults();

  }

  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("Encoder Velocity", ct.getEncodervalue());
    // boop = jax;
    double cfa = joy1.getRawAxis(3);
    double cfc = joy1.getRawAxis(2);
    SmartDashboard.putNumber("goto", ct.getcfb());
    SmartDashboard.putNumber("axis", cfa);
    // ir sensor activate :)
    irs.sendtome();
    
    // if(cfa >= 1){
    // cfb = jax + 0.5;
    // jax += 0.5;
    // }
    // else if (cfc >= 1){
    // cfb = jax + -0.5;
    // jax += -0.5;

    // }
    ct.ChangeTurretAngle(cfa, cfc);

    joy1.setOutput(0, true);
    //if(getRawButton(6)){
      //cam.aimlime();

   // }

    

    // CSM0.set(joy1.getRawAxis(1)*1);
    // CSM1.set(joy1.getRawAxis(1)*-1);

    // double intval = cfb /360 *enc; //*5 *9.05;
    if (joy1.getRawButton(1)) {
      // _talon0.set(ControlMode.PercentOutput,spd);
      ct.turnon();
    } else {
      // _talon0.set(ControlMode.PercentOutput,0);
      ct.turnoff();
    }
    if (joy1.getRawButton(3)) {
      ct.setbbutton(true);
      lm.aimline();
      st.shootit();

      // aimline=limelight and setbbutton shoot turret
    }

    ct.test();
    // if(hbbbp && intval >= 1 ){
    // CSM0.set(spd);

    // boop = CSM0.getEncoder().getPosition();
    SmartDashboard.putNumber("boop", ct.getboop());

    SmartDashboard.putNumber("goto", ct.getintval());

    if (joy1.getRawButton(4)) {

      ct.turnoff();
      ct.setbbutton(false);
      st.shootit();

    }
    if (joy2.getRawButton(1)){
      ibl.eatit();
    }

    if (joy2.getRawButton(2)){
      ibl.spititout();
    }

    if(joy2.getRawButton(3)){
      ibl.imfull();

    }

    if(joy2.getRawButton(4)){
      pnue.pushitout();
      fmm.eatthem();


    }

    if(joy2.getRawButton(5)){
      fmm.imtoofull();
      pnue.bringitin();

    }
    _talon0.set(ControlMode.PercentOutput, joy1.getRawAxis(0), +joy1.getRawAxis(1));
    _talon1.set(ControlMode.PercentOutput, joy1.getRawAxis(0), -joy1.getRawAxis(1));

    // else if(boop >= intval){
    // CSM0.set(0);
    // hbbbp=false;
    // }

    // if(hbbbp && intval <= 0){
    // CSM0.set(-spd);

    // boop = CSM0.getEncoder().getPosition();
    // SmartDashboard.putNumber("boop", boop);

    // SmartDashboard.putNumber("goto", intval);

    // if(joy1.getRawButton(4)){

    // CSM0.set(0);
    // hbbbp = false;

    // }else if(boop <= intval){
    // CSM0.set(0);
    // hbbbp=false;
    // }

    // }

    if (joy1.getRawButton(2)) {
      ct.emergencyreset();
    }
    //SmartDashboard.putNumber("neo", CSM0.getEncoder().getPosition());
    SmartDashboard.putNumber("boop", ct.getboop());

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
