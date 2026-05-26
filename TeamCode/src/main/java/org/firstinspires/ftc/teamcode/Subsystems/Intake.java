package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private final DcMotor intakeMotor;
    private final DcMotor shooterMotor;

    public Intake(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor");

        // Set default directions if needed
        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
        shooterMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    public void setIntakePower(double power) {
        intakeMotor.setPower(power);
    }

    public void setShooterPower(double power) {
        shooterMotor.setPower(power);
    }

    public void stop() {
        intakeMotor.setPower(0);
        shooterMotor.setPower(0);
    }

    public double getIntakePower() {
        return intakeMotor.getPower();
    }

    public double getShooterPower() {
        return shooterMotor.getPower();
    }
}
