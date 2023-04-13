package frc.robot.subsystems;

import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;


public class Drivetrain extends SubsystemBase  {

    
    private WPI_TalonFX m_rightParent, m_leftParent, m_rightChild, m_leftChild;
    private DifferentialDrive differentialDrive;
    private DifferentialDriveOdometry differentialDriveOdometry;
    private AHRS m_Ahrs;

    public double maxPower;

    public Drivetrain(int rightTalonParentID, int leftTalonParentID, int rightTalonChildID, int leftTalonChildID, 
                    boolean rightInversion, boolean leftInversion) {
        m_rightParent = new WPI_TalonFX(rightTalonParentID);
        m_leftParent = new WPI_TalonFX(leftTalonParentID);
        m_rightChild = new WPI_TalonFX(rightTalonChildID);
        m_leftChild = new WPI_TalonFX(leftTalonChildID);
        m_rightChild.follow(m_rightParent);
        m_leftChild.follow(m_leftParent);

        // m_rightParent.configOpenloopRamp(.25);
        // m_leftParent.configOpenloopRamp(.25);

        m_rightParent.setInverted(rightInversion);
        m_rightChild.setInverted(rightInversion);
        m_leftParent.setInverted(leftInversion);
        m_leftChild.setInverted(leftInversion);

        differentialDrive = new DifferentialDrive(m_leftParent, m_rightParent);
        addChild("Differential Drive",differentialDrive);
        differentialDrive.setSafetyEnabled(false);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        m_rightParent.setNeutralMode(NeutralMode.Brake);
        m_rightChild.setNeutralMode(NeutralMode.Brake);
        m_leftParent.setNeutralMode(NeutralMode.Brake);
        m_leftChild.setNeutralMode(NeutralMode.Brake);

        try {
            /***********************************************************************
             * navX-MXP: - Communication via RoboRIO MXP (SPI, I2C) and USB. - See
             * http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
             * 
             * navX-Micro: - Communication via I2C (RoboRIO MXP or Onboard) and USB. - See
             * http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
             * 
             * VMX-pi: - Communication via USB. - See
             * https://vmx-pi.kauailabs.com/installation/roborio-installation/
             * 
             * Multiple navX-model devices on a single robot are supported.
             ************************************************************************/
            SmartDashboard.putString("workie", "workie");
            m_Ahrs = new AHRS(Port.kMXP);
            // m_Ahrs.calibrate();
            // differentialDriveOdometry = new DifferentialDriveOdometry(m_Ahrs.getRotation2d(), getLeftDistance(), getRightDistance());
        } catch (RuntimeException ex) {
            SmartDashboard.putString("No workie rip", "No workie rip");
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
    }

    @Override
    public void periodic() {
        // differentialDriveOdometry.update(m_Ahrs.getRotation2d(), getLeftDistance(), getRightDistance());

        // double xPosition = differentialDriveOdometry.getPoseMeters().getX();
        // double yPosition = differentialDriveOdometry.getPoseMeters().getY();

        // SmartDashboard.putNumber("x position", xPosition);
        // SmartDashboard.putNumber("y position", yPosition);
    }

    public double getLeftDistance() {
        return (m_leftParent.getSelectedSensorPosition() / 10.65) * (0.1524 * Math.PI);
    }

    public double getRightDistance() {
        return (m_rightParent.getSelectedSensorPosition() / 10.65) * (0.1524 * Math.PI);
    }

    public void speedDrive(double drive, double turn, int mode){
        // speed mode: 0 default, 1 speed up, 2 speed down
        // maxPower = 1;
        double turnMultiplier = 0.65;
        if (mode == 1){
            turnMultiplier = 0.5;
            maxPower = 1.0;

        }else if (mode == 2){
            turnMultiplier = 0.8;
            maxPower = 0.2;
        }else{
            maxPower = 0.6;
        }
        double jsDeadBand = Constants.OperatorConstants.kControllerDeadzone;

        double drivePower = 0;
        double turnPower = 0;

        drivePower =  Math.abs(drive) >= jsDeadBand ? -maxPower * Math.pow(drive, 3) : 0;
        turnPower =  Math.abs(turn) >= jsDeadBand ? -maxPower * Math.pow(turn, 3) * turnMultiplier : 0; //the turn is a little more sensitive
        
        SmartDashboard.putNumber("Drive Power", drivePower);
        SmartDashboard.putNumber("Turn Power", turnPower);
        
        // System.out.println(drivePower + "         "+ turnPower);
        differentialDrive.curvatureDrive(drivePower, turnPower, true);
    }

    public void driveStraight(double speed){
        SmartDashboard.putNumber("Drive Speed", speed);
        differentialDrive.tankDrive(speed, speed);
    }

    public float getRoll() {
        return m_Ahrs.getRoll();
    }

    public float getPitch() {
        return m_Ahrs.getPitch();
    }

    public float getYaw() {
        return m_Ahrs.getYaw();
    }
}