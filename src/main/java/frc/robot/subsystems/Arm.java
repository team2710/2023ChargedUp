package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    private WPI_TalonSRX m_ArmTalonSRX;

    public Arm(int armTalonID) {
        m_ArmTalonSRX = new WPI_TalonSRX(armTalonID);

        m_ArmTalonSRX.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
        m_ArmTalonSRX.configFactoryDefault();
        m_ArmTalonSRX.configPeakOutputForward(0.25);
        m_ArmTalonSRX.configPeakOutputReverse(-0.05);

        m_ArmTalonSRX.config_kP(0, 0.5);
        m_ArmTalonSRX.config_kI(0, 0);
        m_ArmTalonSRX.config_kD(0, 0.3);
        m_ArmTalonSRX.config_kF(0, 0.1);

        m_ArmTalonSRX.setNeutralMode(NeutralMode.Coast);

        resetEncoder();
    }

    public void setPower(double power) {
        m_ArmTalonSRX.set(ControlMode.PercentOutput, power);
    }

    public double getRawEncoder() {
        return m_ArmTalonSRX.getSensorCollection().getQuadraturePosition();
    }

    public double getAngle() {
        return getRawEncoder() / 4096 / 4.67 * 360 - Constants.ArmConstants.kStartingAngle;
    }

    public void resetEncoder() {
        m_ArmTalonSRX.getSensorCollection().setQuadraturePosition(0, 30);
    }

    public void setPosition(double setpoint) {
        if (setpoint < 0) setpoint = 0;
        if (setpoint > 5295) setpoint = 5295;
        m_ArmTalonSRX.set(ControlMode.Position, setpoint);

        System.out.println(setpoint);
    }

    public void setSpeed(double speed) {
        m_ArmTalonSRX.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, 0.01);
    }
}
