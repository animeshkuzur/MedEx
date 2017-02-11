<?php

use Illuminate\Database\Seeder;

class PharmacyTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $data = array(
        	['name' => 'Pankaj Medico','location'=>'Delhi'],
        	['name'=> 'Barnwal Pharmacy','location'=>'Lucknow']
            );
        
        DB::table('pharmacies')->insert(
        	$data
        );
    }
}
