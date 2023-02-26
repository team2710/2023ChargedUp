package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ElevatorCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
    private final Drivetrain drivetrain;
    private final Elevator elevator;
    
    private final XboxController driverController;
    private final XboxController auxController;

    public RobotContainer() {
        drivetrain = new Drivetrain(Constants.DriveConstants.kRightParentTalon, Constants.DriveConstants.kLeftParentTalon, Constants.DriveConstants.kRightChildTalon, Constants.DriveConstants.kLeftChildTalon, false, true);
        elevator = new Elevator(16, 17);

        driverController = new XboxController(Constants.OperatorConstants.kDriverControllerPort);
        auxController = new XboxController(Constants.OperatorConstants.kAuxControllerPort);

        drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driverController));
        elevator.setDefaultCommand(new ElevatorCommand(elevator, auxController));
    }

    public void setElevatorPosition(double height) {
        elevator.setPosition(height);
    }

    public double getElevatorPosition() {
        return elevator.getEncoderValue();
    }

    public void setCoastMode() {
        drivetrain.setCoastMode();
    }

    public void setBrakeMode() {
        drivetrain.setBrakeMode();
    }
}
