import { Component, OnInit } from '@angular/core';
import { Rate } from '../rate';
import { RateService } from '../rate.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-rate-list',
  templateUrl: './rate-list.component.html',
  styleUrls: ['./rate-list.component.css'],
})
export class RateListComponent implements OnInit {
  rates: Rate[] = [];
  token: any;
  constructor(private rateService: RateService, private router: Router) {}

  ngOnInit(): void {
    // this.rates = [
    //   {
    //     id: 1,
    //     effDate: '2021-12-12',
    //     expDate: '2024-12-12',
    //     amount: 1234,
    //   },
    //   {
    //     id: 2,
    //     effDate: '2021-12-12',
    //     expDate: '2024-12-12',
    //     amount: 2345,
    //   },
    // ];
    this.getRates();
  }

  //  accessApi(token: any) {
  //     let resp = this.rateService.getRateList(token);
  //     resp.subscribe((data: any) => (this.response = data));
  //   }
  private getRates() {
    //  this.rateService.getRateList().subscribe((data) => {
    this.rateService.getRateList().subscribe((data) => {
      console.log(data);
      this.rates = data;
    });
  }

  rateDetails(rateId: number) {
    this.router.navigate(['rate-details', rateId]);
  }

  updateRate(rateId: number) {
    this.router.navigate(['update-rate', rateId]);
  }

  deleteRate(rateId: number) {
    this.rateService.deleteRate(rateId).subscribe((data) => {
      console.log(data);
      this.getRates();
    });
  }
}


