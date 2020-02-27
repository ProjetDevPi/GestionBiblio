<?php

namespace DocumentBundle\Controller;

use DocumentBundle\Entity\Document;
use DocumentBundle\Entity\Emprunt;
use DocumentBundle\Entity\User;
use DocumentBundle\Form\EmpruntType;
use Knp\Snappy\Pdf;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;

class EmpruntController extends Controller
{

    /**
     * @Route("/Emprunter/{id}", name="emprunter")
     */
    public function EmprunterAction(Document $document, Request $request)
    {

        $emprunt= new Emprunt();
        $form= $this->createForm(EmpruntType::class,$emprunt);
        $em=$this->getDoctrine()->getManager();
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $document = $this->getDoctrine()->getRepository(Document::class)->find($document);
            $user=$this->getUser();
            $emprunt->setIdUser($user);
            $emprunt->setIddocument($document);
            $document->setEmprunte(1);
            $em->persist($emprunt);
            $em->flush();
            $dateemp=$emprunt->getDateemprunt();
            $dateret=$emprunt->getDateretour();
            $result = $dateemp->format('Y-m-d');
            $result1 = $dateret->format('Y-m-d');
            $basic  = new \Nexmo\Client\Credentials\Basic('1a39295c', '7Iul24U8rmw7ZN9i');
            $client = new \Nexmo\Client($basic);
            $message = $client->message()->send([
                'to' => '21692048760',
                'from' => 'kid o',
                'text' => 'votre documenta été emprunté avec succés'
            ]);

            return $this->redirectToRoute('showemprunt');
        }

        return $this->render('@Document/document/page_emprunt.html.twig',array(
            'form'=>$form->createView(),
            'document'=> $document
        ));

    }

    /**
     * @Route("/prolongation/{id}", name="prolongation")
     */
    public function prolongation(Request $request, Emprunt $emprunt)
    {
        $form= $this->createForm(EmpruntType::class,$emprunt);
        $form->remove('dateemprunt');
        $em=$this->getDoctrine()->getManager();
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            $em->flush();
            return $this->redirectToRoute('showemprunt');
        }

        return $this->render('@Document/Emprunt/prolongation.html.twig',array(
            'form'=>$form->createView(),
            'document'=> $emprunt->getIddocument()
        ));
    }

    /**
     * @Route("/showemprunt", name="showemprunt")
     */
    public function showempruntAction()
    {
        $auth_checker = $this->get('security.authorization_checker');
        $this->denyAccessUnlessGranted('ROLE_USER');
        if ($auth_checker->isGranted('ROLE_USER')) {


            $token = $this->container->get('security.token_storage')->getToken()->getUser();
            $user=$token->getId();
            $em = $this->getDoctrine()->getManager();
            $emprunt = $em->getRepository(Emprunt::class)->findEmprunt($user);
            return $this->render('@Document/Emprunt/emprunter.html.twig', array(
                'emprunts'=>$emprunt));
        }
        else
        {
            return $this->redirectToRoute('emprunter');
        }
    }

    /**
     * @Route("/showempruntback", name="showempruntback")
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function showempruntbackAction(Request $request)
    {
        $user = $this->getUser();
        $u = $this->getDoctrine()->getRepository(User::class)->find($user);
        $em= $this->getDoctrine();

        $emprunts=$em->getRepository(Emprunt::class)->findBy(array(

        ));
        if ($request->getMethod() == Request::METHOD_GET) {

            if ($request->get('TELECHARGER_PDF') == "TELECHARGER_PDF") {

                $snappy = new Pdf('C:\pic_2018\wkhtmltopdf\bin\wkhtmltopdf');
                $html = "<style>
          .clearfix:after {
  content: \"\";
  display: table;
  clear: both;
}

a {
  color: #5D6975;
  text-decoration: underline;
}

body {
  position: relative;
  width: 21cm;  
  height: 29.7cm; 
  margin: 0 auto; 
  color: #001028;
  background: #FFFFFF; 
  font-family: Arial, sans-serif; 
  font-size: 12px; 
  font-family: Arial;
}

header {
  padding: 10px 0;
  margin-bottom: 30px;
}

#logo {
  text-align: center;
  margin-bottom: 10px;
}

#logo img {
  width: 90px;
}

h1 {
  border-top: 1px solid  #5D6975;
  border-bottom: 1px solid  #5D6975;
  color: #5D6975;
  font-size: 2.4em;
  line-height: 1.4em;
  font-weight: normal;
  text-align: center;
  margin: 0 0 20px 0;
  background: url(dimension.png);
}

#project {
  float: left;
}

#project span {
  color: #5D6975;
  text-align: right;
  width: 52px;
  margin-right: 10px;
  display: inline-block;
  font-size: 0.8em;
}

#company {
  float: right;
  text-align: right;
}

#project div,
#company div {
  white-space: nowrap;        
}

table {
  width: 100%;
  border-collapse: collapse;
  border-spacing: 0;
  margin-bottom: 20px;
}

table tr:nth-child(2n-1) td {
  background: #F5F5F5;
}

table th,
table td {
  text-align: center;
}

table th {
  padding: 5px 20px;
  color: #5D6975;
  border-bottom: 1px solid #C1CED9;
  white-space: nowrap;        
  font-weight: normal;
}

table .service,
table .desc {
  text-align: left;
}

table td {
  padding: 20px;
  text-align: right;
}

table td.service,
table td.desc {
  vertical-align: top;
}

table td.unit,
table td.qty,
table td.total {
  font-size: 1.2em;
}

table td.grand {
  border-top: 1px solid #5D6975;;
}

#notices .notice {
  color: #5D6975;
  font-size: 1.2em;
}

footer {
  color: #5D6975;
  width: 100%;
  height: 30px;
  position: absolute;
  bottom: 0;
  border-top: 1px solid #C1CED9;
  padding: 8px 0;
  text-align: center;
}</style>
<!DOCTYPE html>
<html lang=\"en\">
  <head>
    <meta charset=\"utf-8\">
    <title>Example 1</title>
    <link rel=\"stylesheet\" href=\"style.css\" media=\"all\" />
  </head>
  <body>
    <header class=\"clearfix\">
      <div id=\"logo\">
        <img src=\"logo.png\">
      </div>
      <h1>EMPRUNTS</h1>
      <div id=\"company\" class=\"clearfix\">
        <div>Cycle.tn</div>
        <div>455 Foggy Heights,<br /> AZ 85004, US</div>
        <div>(602) 519-0450</div>
        <div><a href=\"mailto:company@example.com\">cycle@gmail.com</a></div>
      </div>
      
    </header>
    <main>
      <table>
        <thead>
          <tr>
            <th>DATE EEMPRUNT</th>
            <th>DATE RETOUR EEMPRUNT</th>
            <th>ID EMPRUNT</th>
            <th>NOM USER</th>
          </tr>
        </thead>
        <tbody>";
                foreach ($emprunts as $e) {
                    $html = $html . "<tr>
            <td>" . $e->getDateemprunt()->format('Y-m-d') . "</td>
            <td >" . $e->getDateretour()->format('Y-m-d') . "</td>
            <td>" . $e->getId() . "</td>
            <td>" . $e->getIdUser() . "</td>
          </tr>";

                }
                $html = $html . "
          
        </tbody>
      </table>
      
    </main>
    <footer>
     EMPRUNTS
    </footer>
  </body>
</html>";
                $snappy->generateFromHtml($html, 'C:\sounds\mehdi.pdf');

            }
        }


        return $this->render('@Document/Emprunt/showempruntback.html.twig', array('emprunts' => $emprunts));
    }

    /**
     * @Route("/del1/{id}", name="del1")
     */
    public function del1Action($id)
    {
        $em = $this->getDoctrine()->getManager();



        $emprunt = $em->getRepository(emprunt::class)->find($id);
        $doc=$this->getDoctrine()->getRepository(Document::class )->findOneBy(array('id'=>$emprunt->getiddocument()));
        $doc->setEmprunte(0);
        $em->remove($emprunt);

$em->persist($doc);

        $em->flush();
        return $this->redirectToRoute('showempruntback');
    }

    }
