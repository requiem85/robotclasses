package frc.robot;



import com.ctre.phoenix.motorcontrol.ControlMode;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.RobotDrive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class arcadedrive {

  private TalonFX _frontrfx;

  private TalonFX _frontlfx;

  private TalonFX _backrfx;

  private TalonFX _backlfx;



  private boolean _isReversed = false;

  private boolean _halfSpeed = false;



  // PID

  private static final double kMulti = 1.0;

  private static final int kTimeout_ms = 0;

  private static final int kPidIdx = 0;

  private static final double kTargetVel_rpm = 500.0;

  private static final double kRpm_ms = 600.0;



  // TODO: Get these for each wheel from encoder

  private static final double kHatchElec_tpr = 12824.0; // ticks per rotation

  private static final double kHatchPneu_tpr = -12016.0;

  private static final double kBallElec_tpr = 12372.0;

  private static final double kBallPneu_tpr = -13322.0;



  public arcadedrive(int hePort, int hpPort, int bePort, int bpPort) {

    _frontlfx = new TalonFX(hePort);

    _frontrfx = new TalonFX(hpPort);

    _backrfx = new TalonFX(bePort);

    _backlfx = new TalonFX(bpPort);

  }



  public void init() {

    _frontlfx.configFactoryDefault();

    _frontrfx.configFactoryDefault();

    _backlfx.configFactoryDefault();

    _backrfx.configFactoryDefault();



   // _hatchE.setInverted(true);

    //_ballP.setInverted(true);



   // _hatchE.setSensorPhase(true);

    //_ballP.setSensorPhase(true);



    // TODO: tune this!

   // setupPID(_hatchE, 0, 0, 0.0, 0.0);

    //setupPID(_hatchP, 0, 0, 0.0, 0.0);

    //setupPID(_ballE, 0, 0, 0.0, 0.0);

   //setupPID(_ballP, 0, 0, 0.0, 0.0);

     //setupPID(_frontlfx, 0.1328, 0.11, 0.0, 0); //

     //setupPID(_frontrfx, -0.1247, -0.175, 0.0, 0.0);

     //setupPID(_backrfx, 0.1364, -0.1, 0.0, 0.0);

     //setupPID(_backlfx, -0.1247, -0.15, 0.0, 0.0); //

  }



  public void drive(double driveX, double driveY, double look) {

    // true is PID enabled

    DriveControl controller = calculatePower(driveX, driveY, look, true);



    _frontrfx.set(ControlMode.Velocity, controller.getHatchElec());

    _frontlfx.set(ControlMode.Velocity, controller.getBallElec());

    _backrfx.set(ControlMode.Velocity, controller.getHatchPn());

    _backlfx.set(ControlMode.Velocity, controller.getBallPn());



    SmartDashboard.putNumber("hathE controller velocity", controller.getHatchElec());

    SmartDashboard.putNumber("BallE controller velocity", controller.getBallElec());

    SmartDashboard.putNumber("HatchP controller velocity", controller.getHatchPn());

    SmartDashboard.putNumber("ballP controller velocity", controller.getBallPn());

  }



  public void driveDisabledPID(double driveX, double driveY, double look) {

    // false means PID is disabled

    DriveControl controller = calculatePower(driveX, driveY, look, false);



    _frontlfx.set(ControlMode.PercentOutput, controller.getHatchElec());

    _frontrfx.set(ControlMode.PercentOutput, controller.getBallElec());

    _backlfx.set(ControlMode.PercentOutput, controller.getHatchPn());

    _backrfx.set(ControlMode.PercentOutput, controller.getBallPn());

  }



  public void reverse() {

    _isReversed = (!_isReversed);

  }



  public boolean isReversed() {

    return _isReversed;

  }



  public void setHalfSpeed(boolean hs) {

    _halfSpeed = hs;

  }



  private void setupPID(TalonSRX talon, double kf, double p, double i, double d) {

    talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, kPidIdx, kTimeout_ms);

    talon.setSelectedSensorPosition(0);



    talon.configNominalOutputForward(0.0, kTimeout_ms);

    talon.configNominalOutputReverse(0.0, kTimeout_ms);

    talon.configPeakOutputForward(1.0, kTimeout_ms);

    talon.configPeakOutputReverse(-1.0, kTimeout_ms);



    talon.config_kF(kPidIdx, kf, kTimeout_ms);

    talon.config_kP(kPidIdx, p, kTimeout_ms);

    talon.config_kI(kPidIdx, i, kTimeout_ms);

    talon.config_kD(kPidIdx, d, kTimeout_ms);

  }



  private double getMulti(boolean pidEnabled, double talonTpr) {

    double finalMulti =_halfSpeed ? 0.5 : 1.0;

    finalMulti *= _isReversed ? -kMulti : kMulti;

    finalMulti *= pidEnabled ? kTargetVel_rpm * talonTpr / kRpm_ms : 1.0;

    return finalMulti;

  }



  private DriveControl calculatePower(double driveX, double driveY, double look, boolean pidEnabled) {

    driveX = Utils.applyDeadband(Utils.limit(driveX));

    driveY = Utils.applyDeadband(Utils.limit(driveY));


    // ORIGINAL

    // double fixedLook = look;



    // FIX FOR BALL INVERSION LOOK

    double fixedLook = _isReversed ? -look : look;



    double hatchElecVel = getMulti(pidEnabled, kHatchElec_tpr) * (driveX + driveY + fixedLook); // FL

    double ballElecVel = getMulti(pidEnabled, kBallElec_tpr) * (driveX - driveY - fixedLook); // BL

    double hatchPneVel = getMulti(pidEnabled, kHatchPneu_tpr) * (-driveX + driveY - fixedLook); // FR

    double ballPneVel = getMulti(pidEnabled, kBallPneu_tpr) * (-driveX - driveY + fixedLook); // BR



    return new DriveControl(hatchElecVel, ballElecVel, hatchPneVel, ballPneVel);

  }



  public void debug() {

    SmartDashboard.putString("MECANUM", "");

    SmartDashboard.putBoolean("isDriveReversed?", isReversed());

    SmartDashboard.putNumber("Multi No PID", getMulti(false, 0));



    SmartDashboard.putString("PID", "");

    //debugTalon("right front motor", _frontrfx);

    //debugTalon("left front motor", _frontlfx);

    //debugTalon("left back motor", _backlfx);

    //debugTalon("right front motor", _backrfx);

  }



  private void debugTalon(String key, TalonSRX talon) {

    SmartDashboard.putNumber(key + " Encoder Count", talon.getSelectedSensorPosition());

    SmartDashboard.putNumber(key + " MC Actual", talon.getSelectedSensorVelocity());

    SmartDashboard.putNumber(key + " MC Error", talon.getClosedLoopError());

  }

}