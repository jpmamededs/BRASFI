import { Routes } from '@angular/router';
import { LandingPageComponent } from './pages/landing-page/landing-page.component';
import { FeedComponent } from './pages/feed/feed.component';

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
    {
        path: '**',
        redirectTo: ''
    }
];
