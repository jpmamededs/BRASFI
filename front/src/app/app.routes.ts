import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { FeedComponent } from './pages/feed/feed.component';

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
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
