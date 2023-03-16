package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drivetrain;

public class DriveStraightCommand extends SequentialCommandGroup {
    double seconds, speed;
    Drivetrain drivetrain;

    public DriveStraightCommand(double speed, Drivetrain drivetrain) {
        this.speed = speed;
        this.drivetrain = drivetrain;
        addCommands(
            new RunCommand(() -> {
                drivetrain.driveStraight(speed);
            }, drivetrain));
    }
}
