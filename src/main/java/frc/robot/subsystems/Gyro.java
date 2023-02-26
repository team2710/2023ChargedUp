package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase {
    private AHRS m_AHRS;

    public Gyro() {
        m_AHRS = new AHRS(SerialPort.Port.kMXP);
    }

    public double getDisplacementX() {
        return m_AHRS.getDisplacementX();
    }
    public double getDisplacementY() {
        return m_AHRS.getDisplacementY();
    }

    public double getDisplacementZ() {
        return m_AHRS.getDisplacementZ();
    }

    public double getRoll() {
        return m_AHRS.getRoll();
    }

    public double getPitch() {
        return m_AHRS.getPitch();
    }

    public double getYaw() {
        return m_AHRS.getYaw();
    }
}
