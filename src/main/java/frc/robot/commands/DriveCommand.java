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


        //0 default, 1 speed up, 2 speed down

        drivetrain.speedDrive(moveSpeed, rotateSpeed);
    }
    
}
