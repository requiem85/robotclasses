package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

public class Turret {

    public CANSparkMax CSM0;
    private double boop;
    private double jax;
    private double cfa;
    private double cfb;
    private double cfc;
    private boolean hbbbp;
    private double intval;
    private final double enc = 118;
    private final double timeset = 0.0200;
    public CANEncoder m_encoder;
    public final double spd = 0.1;

    public Turret(int vrm) {

        CSM0 = new CANSparkMax(vrm, CANSparkMaxLowLevel.MotorType.kBrushless);
        CSM0.restoreFactoryDefaults();
        hbbbp = false;
        m_encoder = CSM0.getEncoder();
    }

    public double getcfb(){
        return cfb;
    }
    public double getEncodervalue(){
        return m_encoder.getPosition()/360*enc;
    }
    public double getboop(){
        return boop;
    }
    public double getintval(){
        return intval;
    }
    public void ChangeTurretAngle(double new_cfa, double new_cfc) {
        boop = jax;
        cfa = new_cfa;//joy1.getRawAxis(3);
        cfc = new_cfc; //joy1.getRawAxis(2);
        if (cfa >= 1) {
            cfb = jax + 0.5;
            jax += 0.5;
            //cfb += 0.5;
        } else if (cfc >= 1) {
            cfb = jax + -0.5;
            jax += -0.5;
        }
    }
    public void setbbutton(boolean ibbt ){

        hbbbp = ibbt;
    }

    public void reset(){
        CSM0.restoreFactoryDefaults();  
    }

    public void emergencyreset(){
        CSM0.getEncoder().setPosition(0);
      hbbbp = false;
      cfb = 0;
      jax = 0;
      intval = 0;
      boop = 0;

    }

    public void turnon(){
        CSM0.set(spd);
    }
    public void turnoff(){
        CSM0.set(0);
    }

    
    public void test() {
        // CSM0.set(joy1.getRawAxis(1)*1);
        // CSM1.set(joy1.getRawAxis(1)*-1);

        intval = cfb / 360 * enc; // *5 *9.05;
        // if (joy1.getRawButton(1)) {

        //     CSM0.set(spd);
        // } else {

        //     CSM0.set(0);
        // }
        // if (joy1.getRawButton(3)) {
        //     hbbbp = true;
        // }
        if (hbbbp && intval >= 1) {
            CSM0.set(spd);

            boop = CSM0.getEncoder().getPosition();

            if (boop >= intval) {
                CSM0.set(0);
                hbbbp = false;
            }
        }

        if (hbbbp && intval <= 0) {
            CSM0.set(-spd);

            boop = CSM0.getEncoder().getPosition();

            if (boop <= intval) {
                CSM0.set(0);
                hbbbp = false;
            }

        }

    }


}
