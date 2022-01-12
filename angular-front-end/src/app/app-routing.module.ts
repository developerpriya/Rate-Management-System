import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateRateComponent } from './create-rate/create-rate.component';
import { LoginComponent } from './login/login.component';
import { RateListComponent } from './rate-list/rate-list.component';

import { UpdateRateComponent } from './update-rate/update-rate.component';
import { RateDetailsComponent } from './rate-details/rate-details.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'rates', component: RateListComponent },
  { path: 'create-rate', component: CreateRateComponent },
  { path: '', redirectTo: 'rates', pathMatch: 'full' },
  { path: 'update-rate/:id', component: UpdateRateComponent },
  { path: 'rate-details/:id', component: RateDetailsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
