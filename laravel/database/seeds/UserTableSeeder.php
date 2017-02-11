<?php

use Illuminate\Database\Seeder;

class UserTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table('users')->insert([
        	'name'=>'John Doe',
        	'email'=>'john.doe@gmail.com',
        	'password'=>bcrypt('john@doe'),
        	'image'=>'john.jpg',
        	'sex'=>'Male',
        	'phone'=>'9876543210',
        	'age'=>'20',
        ]);
    }
}
