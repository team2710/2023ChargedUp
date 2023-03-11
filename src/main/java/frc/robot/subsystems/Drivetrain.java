package frc.robot.subsystems;

import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;


public class Drivetrain extends SubsystemBase  {
    private WPI_TalonFX m_rightParent, m_leftParent, m_rightChild, m_leftChild;
    public DifferentialDrive differentialDrive;
    public DifferentialDriveOdometry differentialDriveOdometry;

    public static enum SpeedMode {
        DEFAULT, UP, DOWN
    }

    private SpeedMode mode;

    public Drivetrain(int rightTalonParentID, int leftTalonParentID, int rightTalonChildID, int leftTalonChildID, 
                    boolean rightInversion, boolean leftInversion) {
        m_rightParent = new WPI_TalonFX(rightTalonParentID);
        m_leftParent = new WPI_TalonFX(leftTalonParentID);
        m_rightChild = new WPI_TalonFX(rightTalonChildID);
        m_leftChild = new WPI_TalonFX(leftTalonChildID);
        m_rightChild.follow(m_rightParent);
        m_leftChild.follow(m_leftParent);

        m_rightParent.setInverted(rightInversion);
        m_rightChild.setInverted(rightInversion);
        m_leftParent.setInverted(leftInversion);
        m_leftChild.setInverted(leftInversion);

        differentialDrive = new DifferentialDrive(m_leftParent, m_rightParent);
        addChild("Differential Drive",differentialDrive);
        differentialDrive.setSafetyEnabled(true);
        differentialDrive.setExpiration(0.1);
        differentialDrive.setMaxOutput(1.0);

        // differentialDriveOdometry = new DifferentialDriveOdometry(Units.inchesToMeters(23));

        m_rightParent.setNeutralMode(NeutralMode.Brake);
        m_rightChild.setNeutralMode(NeutralMode.Brake);
        m_leftParent.setNeutralMode(NeutralMode.Brake);
        m_leftChild.setNeutralMode(NeutralMode.Brake);
    }

    public void setSpeedMode(SpeedMode mode) {
        this.mode = mode;
    }

    public void speedDrive(double drive, double turn) {
        double maxPower = 0.0;
        double turnMultiplier = 0.0;
        switch (mode) {
            case DEFAULT:
                maxPower = 0.6;
                turnMultiplier = 0.6;
            case DOWN:
                maxPower = 0.2;
                turnMultiplier = 1.0;
            case UP:
                maxPower = 1.0;
                turnMultiplier = 0.5;
        }


        double jsDeadBand = Constants.OperatorConstants.kControllerDeadzone;

        double drivePower = 0;
        double turnPower = 0;

        drivePower =  Math.abs(drive) >= jsDeadBand ? -maxPower * Math.pow(drive, 3) : 0;
        turnPower =  Math.abs(turn) >= jsDeadBand ? -maxPower * Math.pow(turn, 3) * turnMultiplier : 0; //the turn is a little more sensitive
        
        differentialDrive.curvatureDrive(drivePower, turnPower, true);
    }

    public void driveStraight(double speed){
        differentialDrive.tankDrive(-speed, -speed);
    }
    
}