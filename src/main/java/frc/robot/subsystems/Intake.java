package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    WPI_TalonSRX m_IntakeTalonSRX;

    public Intake(int intakeTalonSRXID) {
        m_IntakeTalonSRX = new WPI_TalonSRX(intakeTalonSRXID);
    }

    public void cubeIntake() {
        m_IntakeTalonSRX.set(0.5);
    }

    public void coneIntake() {
        m_IntakeTalonSRX.set(-0.5);
    }

    public void coneHold() {
        m_IntakeTalonSRX.set(-0.1);
    }

    public void coneRelease() {
        m_IntakeTalonSRX.set(-0.1);
    }

    public void setSpeed(double speed) {
        m_IntakeTalonSRX.set(speed);
    }
}
