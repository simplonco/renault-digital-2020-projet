import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CarModelListComponent} from './car-model-list/car-model-list.component';
import {BrandListComponent} from './brand-list/brand-list.component';
import {CarFormComponent} from './car-form/car-form.component';
import {FormsModule} from "@angular/forms";
import {LoginComponent} from './login/login.component';
import {LogoutComponent} from './logout/logout.component';

@NgModule({
  declarations: [
    AppComponent,
    CarModelListComponent,
    BrandListComponent,
    CarFormComponent,
    LoginComponent,
    LogoutComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
