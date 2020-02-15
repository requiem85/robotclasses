package frc.robot;



public class DriveControl {

  private double _frontrfx;

  private double _frontlfx;

  private double _backrfx;

  private double _backlfx;



  public DriveControl(double he, double be, double hp, double bp) {

    _frontlfx = he;

    _frontrfx = be;

    _backlfx = hp;

    _backrfx = bp;

  }



  public double getHatchElec() {

    return _frontlfx;

  }



  public double getBallElec() {

    return _frontrfx;

  }



  public double getHatchPn() {

    return _backlfx;

  }



  public double getBallPn() {

    return _backrfx;

  }

}