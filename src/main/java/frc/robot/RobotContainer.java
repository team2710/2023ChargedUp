package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArmCommand;
import frc.robot.commands.ArmMoveCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.DriveStraightCommand;
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.ElevatorMoveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ZeroArm;
import frc.robot.commands.Auto.BalanceCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
    public static Drivetrain drivetrain;
    public static Elevator elevator;
    public static Arm arm;
    public static Intake intake;
    public static boolean isBalancing;
    
    public static CommandXboxController driverController;
    public static CommandXboxController auxController;

    public RobotContainer() {
        drivetrain = new Drivetrain(Constants.DriveConstants.kRightParentTalon, Constants.DriveConstants.kLeftParentTalon, Constants.DriveConstants.kRightChildTalon, Constants.DriveConstants.kLeftChildTalon, false, true);
        elevator = new Elevator(Constants.ElevatorConstants.kLeftSparkmax, Constants.ElevatorConstants.kRightSparkmax);
        arm = new Arm(Constants.ArmConstants.kArmTalonSRX);
        intake = new Intake(Constants.ArmConstants.kIntakeSparkmax);

        driverController = new CommandXboxController(Constants.OperatorConstants.kDriverControllerPort);
        auxController = new CommandXboxController(Constants.OperatorConstants.kAuxControllerPort);
    }

    public void configureButtonBindings() {
        Trigger rightBumper = auxController.rightBumper();
        Trigger driverPovDown = driverController.povDown();
        Trigger leftBumper = auxController.leftBumper();
        rightBumper.onTrue(new ElevatorMoveCommand(elevator, Constants.ElevatorConstants.kElevatorMax)
            .alongWith(new DriveCommand(drivetrain, driverController, 2))
            .alongWith(new ArmMoveCommand(arm, 5250)));
        leftBumper.onTrue(new ElevatorMoveCommand(elevator, 0)
            .andThen(new WaitCommand(0.5))
            .andThen(new DriveCommand(drivetrain, driverController, 0))
            .alongWith(new ArmMoveCommand(arm, 0)));

        // Trigger bButton = auxController.b();
        Trigger aButton = auxController.a();
        // bButton.onTrue(new ArmMoveCommand(arm, 5500));
        aButton.onTrue(new ArmMoveCommand(arm, 0));

        driverPovDown.onTrue(new ZeroArm(arm));

        Trigger driverLeftBumper = driverController.leftBumper();
        driverLeftBumper.whileTrue(new DriveCommand(drivetrain, driverController, 1));
        Trigger driverRightBumper = driverController.rightBumper();
        driverRightBumper.whileTrue(new DriveCommand(drivetrain, driverController, 2));


        Trigger yButton = auxController.y();
        Trigger xButton = auxController.x();
        Trigger povDownButton = auxController.povDown();
        yButton.onTrue(new IntakeCommand(intake, 25, Constants.ArmConstants.kIntake))
            .onFalse(new IntakeCommand(intake, 5, Constants.ArmConstants.kIntakeHold));
        xButton.onTrue(new IntakeCommand(intake, 25, Constants.ArmConstants.KOuttake * -1))
            .onFalse(new IntakeCommand(intake, 25, 0));
        povDownButton.onTrue(new IntakeCommand(intake, 25, 0));

        Trigger rightButtonTrigger = auxController.rightStick();
        rightButtonTrigger.onTrue(new ArmMoveCommand(arm, 2000));

        Trigger povRightButton = auxController.povRight();
        povRightButton.onTrue(new ElevatorMoveCommand(elevator, Constants.ElevatorConstants.kCubeMid)
            .alongWith(new DriveCommand(drivetrain, driverController, 2))
            .alongWith(new ArmMoveCommand(arm, 4500)));

        Trigger bButton = auxController.b();
        bButton.onTrue(new ElevatorMoveCommand(elevator, Constants.ElevatorConstants.kCubeMid+1)
                .alongWith(new DriveCommand(drivetrain, driverController, 2))
                .alongWith(new ArmMoveCommand(arm, 5250)));
        // yButton.onFalse()
    }

    public void runTeleop() {
        drivetrain.setDefaultCommand(new DriveCommand(drivetrain, driverController, 0));
        elevator.setDefaultCommand(new ElevatorCommand(elevator, auxController));
        arm.setDefaultCommand(new ArmCommand(arm, intake, auxController));
        configureButtonBindings();
    }

    public void runAutos(String scoringAuto, String taxiAuto) {
        SequentialCommandGroup scoringAutoCommand = new SequentialCommandGroup();
        switch (scoringAuto) {
            case "Cube Mid":
                scoringAutoCommand.addCommands(
                    new ArmMoveCommand(arm, 5250),
                    new ElevatorMoveCommand(elevator,Constants.ElevatorConstants.kCubeMid+1),
                    new WaitCommand(2),
                    new IntakeCommand(intake, 25, 0.5),
                    new WaitCommand(1),
                    new IntakeCommand(intake, 25, 0),
                    new WaitCommand(1),
                    new ElevatorMoveCommand(elevator, 0),
                    new ArmMoveCommand(arm, 0)
                );
                break;
            case "Cube Low":
                scoringAutoCommand.addCommands(
                    new ArmMoveCommand(arm, 5150),
                    new WaitCommand(2),
                    new IntakeCommand(intake, 25, 0.5),
                    new WaitCommand(1),
                    new IntakeCommand(intake, 25, 0),
                    new WaitCommand(1),
                    new ElevatorMoveCommand(elevator, 0),
                    new ArmMoveCommand(arm, 0)
                );
                break;
        }

        SequentialCommandGroup taxiAutoCommand = new SequentialCommandGroup();

        switch (taxiAuto) {
            case "Taxi":
                taxiAutoCommand.addCommands(
                    new InstantCommand(() -> {
                        drivetrain.driveStraight(-0.5);
                    }),
                    new WaitCommand(4),
                    new InstantCommand(() -> {
                        drivetrain.driveStraight(0);
                    })
                );
                break;
            case "Auto Balance":
                taxiAutoCommand.addCommands(new InstantCommand(() -> {
                    drivetrain.driveStraight(-0.5);
                }), new RunCommand(() -> {
                    double speed = 0.4;
                    if (Math.abs(drivetrain.getRoll())-3.56 > 1) {
                        if (Math.signum(drivetrain.getRoll()) > 0)
                            speed = 0.3;
                        drivetrain.driveStraight(speed * Math.signum(drivetrain.getRoll()));
                        isBalancing = true;
                        SmartDashboard.putBoolean("is balancing", true);
                    } else if(isBalancing) {
                        drivetrain.driveStraight(0);
                    }
                }, drivetrain));
            case "No Taxi":
                break;
        }

        SmartDashboard.putBoolean("is balancing", false);
        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(scoringAutoCommand, taxiAutoCommand));
    }

    public void updateSmartdashboard() {
        // SmartDashboard.putNumber("Wheel Rotation", drivetrain.m_leftParent.getSelectedSensorPosition());
        SmartDashboard.putNumber("Roll", drivetrain.getRoll());
        SmartDashboard.putNumber("Pitch", drivetrain.getPitch());
        SmartDashboard.putNumber("Yaw", drivetrain.getYaw());
    }
}
