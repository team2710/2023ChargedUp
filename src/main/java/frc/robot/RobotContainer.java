package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.ArmMoveCommand;
import frc.robot.commands.ConeHoldCommand;
import frc.robot.commands.ConeIntakeCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.ElevatorMoveCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
    private final Drivetrain drivetrain;
    private final Elevator elevator;
    private final Arm arm;
    private final Intake intake;
    
    private final CommandXboxController driverController;
    private final CommandXboxController auxController;

    public RobotContainer() {
        drivetrain = new Drivetrain(Constants.DriveConstants.kRightParentTalon, Constants.DriveConstants.kLeftParentTalon, Constants.DriveConstants.kRightChildTalon, Constants.DriveConstants.kLeftChildTalon, false, true);
        elevator = new Elevator(Constants.ElevatorConstants.kLeftSparkmax, Constants.ElevatorConstants.kRightSparkmax);
        arm = new Arm(Constants.ArmConstants.kArmTalonSRX);
        intake = new Intake(Constants.ArmConstants.kIntakeTalonSRX);

        driverController = new CommandXboxController(Constants.OperatorConstants.kDriverControllerPort);
        auxController = new CommandXboxController(Constants.OperatorConstants.kAuxControllerPort);

        drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driverController));
        elevator.setDefaultCommand(new ElevatorCommand(elevator, auxController));
        arm.setDefaultCommand(new ArmCommand(arm, intake, auxController));

        configureButtonBindings();
    }

    public void configureButtonBindings() {
        Trigger rightBumper = auxController.rightBumper();
        Trigger leftBumper = auxController.leftBumper();
        rightBumper.onTrue(new ElevatorMoveCommand(elevator, Constants.ElevatorConstants.kElevatorMax));
        leftBumper.onTrue(new ElevatorMoveCommand(elevator, 0));

        Trigger aButton = auxController.b();
        Trigger xButton = auxController.a();
        aButton.onTrue(new ArmMoveCommand(arm, 4800));
        xButton.onTrue(new ArmMoveCommand(arm, 0));

        // Trigger yButton = auxController.x();
        // yButton.onTrue(new ConeIntakeCommand(intake).andThen(new WaitCommand(2)).andThen(new ConeHoldCommand(intake)));
        // yButton.onFalse()
    }

    public void resetArm() {
        arm.resetEncoder();
    }

    public void setElevatorPosition(double height) {
        elevator.setPosition(height);
    }

    public double getElevatorPosition() {
        return elevator.getEncoderValue();
    }

    // public void setCoastMode() {
    //     drivetrain.setCoastMode();
    // }

    // public void setBrakeMode() {
    //     drivetrain.setBrakeMode();
    // }
}
