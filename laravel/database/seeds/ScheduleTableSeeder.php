<?php

use Illuminate\Database\Seeder;

class ScheduleTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $data = array(
        	['user_id' =>1 ,'medicine_id'=>2,'dosage'=>2,'time'=>'0600','day'=>'0'],
        	['user_id' =>1 ,'medicine_id'=>3,'dosage'=>5,'time'=>'1300','day'=>'4']
            );
        
        DB::table('schedules')->insert(
        	$data
        );
    }
}
