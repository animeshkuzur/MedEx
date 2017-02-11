<?php

use Illuminate\Database\Seeder;

class UserMedicineTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $data = array(
        	['user_id' =>1 ,'medicine_id'=>2,'qty'=>2,'expiry_date'=>'2017-10-20'],
        	['user_id' =>1 ,'medicine_id'=>3,'qty'=>5,'expiry_date'=>'2018-09-07']
            );
        
        DB::table('user_medicines')->insert(
        	$data
        );
    }
}
