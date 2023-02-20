package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
    private final Drivetrain drivetrain;
    
    private final XboxController driverController;
    private final XboxController auxController;

    public RobotContainer() {
        drivetrain = new Drivetrain(0, 0, 0, 0, false, false);
        
        driverController = new XboxController(Constants.OperatorConstants.kDriverControllerPort);
        auxController = new XboxController(Constants.OperatorConstants.kAuxControllerPort);

        drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driverController));
    }

    public void setCoastMode() {
        drivetrain.setCoastMode();
    }

    public void setBrakeMode() {
        drivetrain.setBrakeMode();
    }
}
