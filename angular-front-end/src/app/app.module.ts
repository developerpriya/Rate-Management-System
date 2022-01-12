import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RateListComponent } from './rate-list/rate-list.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { CreateRateComponent } from './create-rate/create-rate.component';
import { ReactiveFormsModule } from '@angular/forms';
import { UpdateRateComponent } from './update-rate/update-rate.component';
import { RateDetailsComponent } from './rate-details/rate-details.component';

@NgModule({
  declarations: [
    AppComponent,
    RateListComponent,
    LoginComponent,
    CreateRateComponent,
    UpdateRateComponent,
    RateDetailsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
