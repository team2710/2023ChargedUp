package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    @Override
    public void robotInit() {
        RobotContainer.init();
        RobotContainer.arm.resetEncoder();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void autonomousInit() {
        RobotContainer.runAutos();
    }

    @Override
    public void autonomousPeriodic() {
        
    }
}
