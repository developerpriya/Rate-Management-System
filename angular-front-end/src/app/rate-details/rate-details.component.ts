import { Component, OnInit } from '@angular/core';
import { Rate } from '../rate';
import { Rated } from '../resttemplatevo';
import { RateService } from '../rate.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-rate-details',
  templateUrl: './rate-details.component.html',
  styleUrls: ['./rate-details.component.css'],
})
export class RateDetailsComponent implements OnInit {
  id!: number;
  rate: Rate = new Rate();
  rates: Rated = new Rated();
  constructor(
    private rateService: RateService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.rateService.getRateById(this.id).subscribe(
      (data) => {
        this.rates = data;
      },
      (error) => console.log(error)
    );
  }
}
