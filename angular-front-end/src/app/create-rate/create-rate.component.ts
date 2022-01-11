import { Component, OnInit } from '@angular/core';
import { Rate } from '../rate';
import { RateService } from '../rate.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-rate',
  templateUrl: './create-rate.component.html',
  styleUrls: ['./create-rate.component.css'],
})
export class CreateRateComponent implements OnInit {
  rate: Rate = new Rate();

  constructor(private rateService: RateService, private router: Router) {}

  ngOnInit(): void {}

  saveRate() {
    this.rateService.createRate(this.rate).subscribe(
      (data) => {
        console.log(data);
         this.goToRateList();
      },
      (error) => console.log(error)
    );
  }

  goToRateList() {
    this.router.navigate(['/rates']);
  }

  onSubmit() {
    console.log(this.rate);
     this.saveRate();
  }
}
