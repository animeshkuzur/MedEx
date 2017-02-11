<?php

use Illuminate\Database\Seeder;

class PharmacyMedicineTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $data = array(
        	['pharmacy_id' =>1 ,'medicine_id'=>2,'qty'=>2,'expiry_date'=>'2017-10-20'],
        	['pharmacy_id' =>1 ,'medicine_id'=>3,'qty'=>5,'expiry_date'=>'2018-09-07']
            );
        
        DB::table('pharmacy_medicines')->insert(
        	$data
        );
    }
}
