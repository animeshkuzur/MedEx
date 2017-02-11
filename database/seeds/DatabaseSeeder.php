<?php

use Illuminate\Database\Seeder;

class DatabaseSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $this->call(UserTableSeeder::class);
        $this->call(MedicineTableSeeder::class);
        $this->call(UserMedicineTableSeeder::class);
        $this->call(PharmacyTableSeeder::class);
        $this->call(PharmacyMedicineTableSeeder::class);
        $this->call(ScheduleTableSeeder::class);
    }
}
