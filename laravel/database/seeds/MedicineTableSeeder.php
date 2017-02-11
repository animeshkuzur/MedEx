<?php

use Illuminate\Database\Seeder;

class MedicineTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $data = array(
        	['name' => 'Medicine 1','description'=>'dummy data','image'=>'image1.jpg','manufacturer'=>'pankaj'],
        	['name' => 'Medicine 2','description'=>'dummy data','image'=>'image2.jpg','manufacturer'=>'pankaj'],
        	['name' => 'Medicine 3','description'=>'dummy data','image'=>'image3.jpg','manufacturer'=>'pankaj'],
        	['name' => 'Medicine 4','description'=>'dummy data','image'=>'image4.jpg','manufacturer'=>'pankaj'],
        	['name' => 'Medicine 5','description'=>'dummy data','image'=>'image5.jpg','manufacturer'=>'pankaj']
            );
        
        DB::table('medicines')->insert(
        	$data
        );
    }
}
