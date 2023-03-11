package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.ArmMoveCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.ElevatorMoveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
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
        intake = new Intake(Constants.ArmConstants.kIntakeSparkmax);

        driverController = new CommandXboxController(Constants.OperatorConstants.kDriverControllerPort);
        auxController = new CommandXboxController(Constants.OperatorConstants.kAuxControllerPort);

        drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driverController, 0));
        elevator.setDefaultCommand(new ElevatorCommand(elevator, auxController));
        arm.setDefaultCommand(new ArmCommand(arm, intake, auxController));
        // intake.setDefaultCommand(new IntakeTemperatureCommand(intake));

        configureButtonBindings();
    }

    public void configureButtonBindings() {
        Trigger rightBumper = auxController.rightBumper();
        Trigger leftBumper = auxController.leftBumper();
        rightBumper.onTrue(new ElevatorMoveCommand(elevator, Constants.ElevatorConstants.kElevatorMax).alongWith(new DriveCommand(drivetrain, driverController, 2)));
        leftBumper.onTrue(new ElevatorMoveCommand(elevator, 0).andThen(new WaitCommand(0.5)).andThen(new DriveCommand(drivetrain, driverController, 0)));

        Trigger bButton = auxController.b();
        Trigger aButton = auxController.a();
        bButton.onTrue(new ArmMoveCommand(arm, 4800));
        aButton.onTrue(new ArmMoveCommand(arm, 0));

        Trigger driverLeftBumper = driverController.leftBumper();
        driverLeftBumper.whileTrue(new DriveCommand(drivetrain, driverController, 1));
        Trigger driverRightBumper = driverController.rightBumper();
        driverRightBumper.whileTrue(new DriveCommand(drivetrain, driverController, 2));


        Trigger yButton = auxController.y();
        Trigger xButton = auxController.x();
        Trigger povUpButton = auxController.povUp();
        Trigger povDownButton = auxController.povDown();
        yButton.onTrue(new IntakeCommand(intake, 25, Constants.ArmConstants.kIntake));
        xButton.onTrue(new IntakeCommand(intake, 25, Constants.ArmConstants.kIntake * -1));
        povUpButton.onTrue(new IntakeCommand(intake, 5, Constants.ArmConstants.kIntakeHold));
        povDownButton.onTrue(new IntakeCommand(intake, 25, 0));
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

    public void runAutos() {
        CommandScheduler.getInstance().schedule(new ArmMoveCommand(arm, 4800).alongWith(new ElevatorMoveCommand(elevator,Constants.ElevatorConstants.kElevatorMax).andThen(new WaitCommand(2).andThen(new IntakeCommand(intake, 25, -0.25)))));
    }

    // public void setCoastMode() {
    //     drivetrain.setCoastMode();
    // }

    // public void setBrakeMode() {
    //     drivetrain.setBrakeMode();
    // }
}
