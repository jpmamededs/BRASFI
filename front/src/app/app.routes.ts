import { Routes } from '@angular/router';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { FeedComponent } from './pages/feed/feed.component';
import { PlataformComponent } from './pages/plataform/plataform.component';
import { LoginComponent } from './login/login.component';
import { authGuard } from './services/auth.guard';
import { SignupComponent } from './signup/signup.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';

export const routes: Routes = [
    {
        path: '',
        component: LandingPageComponent,
        canActivate: []
    },
    {
        path: 'noticias',
        component: FeedComponent,
        canActivate: []
    },

    { path: 'plataforma', component: PlataformComponent, canActivate: [authGuard] },

  { path: 'edit-profile', component: EditProfileComponent, canActivate: [authGuard] },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  {

        path: 'login',
        component: LoginComponent,
        canActivate: []

        },

   {

    path:'cadastro',component:SignupComponent
   }
];




