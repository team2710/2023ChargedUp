package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;

public class ElevatorCommand extends CommandBase {

    private final Elevator elevator;
    private final XboxController auxController;

    public ElevatorCommand(Elevator elevator, XboxController auxController) {
        this.elevator = elevator;
        this.auxController = auxController;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        // elevator.setPosition(7.5);
        SmartDashboard.putNumber("Elevator Set Position", 0);
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Elevator Position", elevator.getEncoderValue());

        double position = SmartDashboard.getNumber("Elevator Set Position", 0);
        elevator.setPosition(position);
    }
    
}
