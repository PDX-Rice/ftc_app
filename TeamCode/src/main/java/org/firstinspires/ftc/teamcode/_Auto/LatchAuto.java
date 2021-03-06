package org.firstinspires.ftc.teamcode._Auto;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import java.util.Locale;
@Autonomous(name="LatchAuto", group="Autonomous")
public class LatchAuto extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftfrontDrive = null;
    private DcMotor rightfrontDrive = null;
    private DcMotor leftbackDrive = null;
    private DcMotor rightbackDrive = null;

    @Override
    public void runOpMode() {

        leftfrontDrive = hardwareMap.get(DcMotor.class, "frontLeft");
        leftfrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightfrontDrive = hardwareMap.get(DcMotor.class, "frontRight");
        rightfrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftbackDrive = hardwareMap.get(DcMotor.class, "backLeft");
        leftbackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftbackDrive.setDirection(DcMotor.Direction.REVERSE);

        rightbackDrive = hardwareMap.get(DcMotor.class, "backRight");
        rightbackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        driveEncoder();

    }
    public void driveEncoder(){
        leftfrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightfrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftbackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightbackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftfrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightfrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftbackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightbackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //leftfrontDrive.setTargetPosition(1120);
        //rightfrontDrive.setTargetPosition(1120);
        //leftbackDrive.setTargetPosition(1120);
        //rightbackDrive.setTargetPosition(1120);
        //Wheel circumference ~ 31.5cm

        leftfrontDrive.setTargetPosition(1120);
        rightfrontDrive.setTargetPosition(1120);
        leftbackDrive.setTargetPosition(1120);
        rightbackDrive.setTargetPosition(1120);

        leftfrontDrive.setPower(0.2);
        rightfrontDrive.setPower(0.2);
        leftbackDrive.setPower(0.2);
        rightbackDrive.setPower(0.2);

        while(leftfrontDrive.isBusy() && rightfrontDrive.isBusy() && leftbackDrive.isBusy() && rightbackDrive.isBusy() && opModeIsActive()) {

        }

        leftfrontDrive.setPower(0);
        rightfrontDrive.setPower(0);
        leftbackDrive.setPower(0);
        leftbackDrive.setPower(0);

        runtime.reset();


        leftfrontDrive.setTargetPosition(-1120);
        rightfrontDrive.setTargetPosition(-1120);
        leftbackDrive.setTargetPosition(-1120);
        rightbackDrive.setTargetPosition(-1120);

        leftfrontDrive.setPower(-0.2);
        rightfrontDrive.setPower(-0.2);
        leftbackDrive.setPower(-0.2);
        rightbackDrive.setPower(-0.2);

        while(leftfrontDrive.isBusy() && rightfrontDrive.isBusy() && leftbackDrive.isBusy() && rightbackDrive.isBusy() && opModeIsActive()) {

        }

        leftfrontDrive.setPower(0);
        rightfrontDrive.setPower(0);
        leftbackDrive.setPower(0);
        leftbackDrive.setPower(0);

    }
}