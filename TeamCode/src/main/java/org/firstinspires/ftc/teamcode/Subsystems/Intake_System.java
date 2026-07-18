package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake_System {
    private final DcMotor intakeMotor;
    private final DcMotor shooterMotor;

    public Intake_System(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor"); //0
        shooterMotor = hardwareMap.get(DcMotor.class, "shooterMotor"); //1
        intakeMotor.setDirection(DcMotor.Direction.FORWARD);
        shooterMotor.setDirection(DcMotor.Direction.FORWARD);
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

    public void update(Telemetry telemetry, Gamepad gamepad) {
        double intakePower = gamepad.left_trigger > 0.1 ? -1.0 : 0.0;
        double shooterPower = gamepad.right_trigger > 0.1 ? -1.0 : 0.0;
        
        setIntakePower(intakePower);
        setShooterPower(shooterPower);
        
        telemetry.addData("Intake/Shooter", "Intake:%.2f Shooter:%.2f",
                intakeMotor.getPower(), shooterMotor.getPower());
    }

    public void autoShoot(double shooter_power) {
        shooterMotor.setPower(shooter_power);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        intakeMotor.setPower(1);
    }

    public void autoIntake() {
        intakeMotor.setPower(1);
    }
}
