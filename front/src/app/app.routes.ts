import { Routes } from '@angular/router';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { FeedComponent } from './pages/feed/feed.component';
import { PlataformComponent } from './pages/plataform/plataform.component';
import { LoginComponent } from './login/login.component';
import { authGuard } from './services/auth.guard';
import { SignupComponent } from './signup/signup.component';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ListagemComponent } from './pages/listagem/listagem.component';
import { DetalhesPageComponent } from './pages/listagem/detalhes-page/detalhes-page.component';
import { ContactComponent } from './pages/contact/contact.component';

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

    { path: 'plataforma', component: PlataformComponent, canActivate: [authGuard]},

  { path: 'edit-profile', component: EditProfileComponent  },
  { path: 'contato', component: ContactComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },

   {

    path:'cadastro',component:SignupComponent
   },

   {
    path:'feed', component:FeedComponent
   },
   { path: 'listagem/:categoria', component: ListagemComponent },

   {

    path:'detalhes/:id',component:DetalhesPageComponent
   }
];




