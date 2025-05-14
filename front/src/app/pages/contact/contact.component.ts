import { Component } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { NavbarComponent } from "../landing-page/components/navbar/navbar.component";
import { FooterComponent } from "../landing-page/components/footer/footer.component";

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
  standalone: true,
  imports: [ReactiveFormsModule, NavbarComponent, FooterComponent],  // Adicionando o módulo necessário
})
export class ContactComponent {
  contactForm = new FormGroup({
    subject: new FormControl(''),
    name: new FormControl(''),
    message: new FormControl('')
  });

  sendMessage() {
    const subject = this.contactForm.value.subject;
    const name = this.contactForm.value.name;
    const message = this.contactForm.value.message;
    const whatsappLink = `https://wa.me/558387396978?text=Assunto:%20${subject}%0ANome:%20${name}%0AMensagem:%20${message}`;
    window.open(whatsappLink, '_blank');
  }
}
