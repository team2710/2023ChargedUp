package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class DriveCommand extends CommandBase {

    private final Drivetrain drivetrain;
    private final CommandXboxController controller;

    public DriveCommand(Drivetrain drivetrain, CommandXboxController controller) {
        this.drivetrain = drivetrain;
        this.controller = controller;
        addRequirements(drivetrain);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double moveSpeed = controller.getRawAxis(1);
        double rotateSpeed = controller.getRawAxis(2);

        drivetrain.speedDrive(moveSpeed, rotateSpeed);



        // if (Math.abs(moveSpeed) > Constants.OperatorConstants.kControllerDeadzone || 
        //     Math.abs(rotateSpeed) > Constants.OperatorConstants.kControllerDeadzone) {
        //         drivetrain.arcadeDrive(
        //             -Math.pow(moveSpeed, 3), 
        //             Math.pow(rotateSpeed, 3)
        //         );
        //     }

        // double leftSpeed = controller.getRawAxis(1);
        // double rightSpeed = controller.getRawAxis(3);
        // if (Math.abs(leftSpeed) > Constants.OperatorConstants.kControllerDeadzone || 
        //     Math.abs(rightSpeed) > Constants.OperatorConstants.kControllerDeadzone) {
        //         // drivetrain.arcadeDrive(
        //         //     -Math.pow(moveSpeed, 2) * Math.signum(moveSpeed), 
        //         //     -Math.pow(rotateSpeed, 2) * Math.signum(rotateSpeed)
        //         // );
        //         drivetrain.tankDrive(
        //             -Math.pow(leftSpeed, 2) * Math.signum(leftSpeed),
        //             -Math.pow(rightSpeed, 2) * Math.signum(rightSpeed));
        //         // SmartDashboard.putNumber("Move Speed", -Math.pow(moveSpeed, 2) * Math.signum(moveSpeed));
        //         // SmartDashboard.putNumber("Rotate Speed", -Math.pow(rotateSpeed, 2) * Math.signum(rotateSpeed));
        //     }


    }
    
}
