package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class DriveCommand extends CommandBase {

    private final Drivetrain drivetrain;
    private final XboxController controller;

    public DriveCommand(Drivetrain drivetrain, XboxController controller) {
        this.drivetrain = drivetrain;
        this.controller = controller;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double moveSpeed = -controller.getRawAxis(1);
        double rotateSpeed = -controller.getRawAxis(2);
        if (Math.abs(moveSpeed) > Constants.OperatorConstants.kControllerDeadzone || 
            Math.abs(rotateSpeed) > Constants.OperatorConstants.kControllerDeadzone) {
                drivetrain.arcadeDrive(moveSpeed, rotateSpeed);
            }
    }
    
}
