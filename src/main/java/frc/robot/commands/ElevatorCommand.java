package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

public class ElevatorCommand extends CommandBase {

    private final Elevator elevator;
    // private final CommandXboxController auxController;
    private final CommandPS4Controller auxController;

    // public ElevatorCommand(Elevator elevator, CommandXboxController auxController) {
    //     this.elevator = elevator;
    //     this.auxController = auxController;
    //     addRequirements(elevator);
    // }

    public ElevatorCommand(Elevator elevator, CommandPS4Controller auxController) {
        this.elevator = elevator;
        this.auxController = auxController;
        addRequirements(elevator);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void initialize() {
        // elevator.setPosition(7.5);
        // SmartDashboard.putNumber("Elevator Set Position", 0);
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Elevator Position", elevator.getEncoderValue());

        // double rightY = auxController.getRightY();

        // double position = SmartDashboard.getNumber("Elevator Set Position", 0);
        // // position = rightY;
        // // SmartDashboard.putNumber("Elevator Position lol", rightY);
        // elevator.setPosition(position);
    }
    
}
