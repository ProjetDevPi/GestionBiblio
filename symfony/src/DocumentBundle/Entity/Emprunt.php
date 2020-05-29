<?php

namespace DocumentBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Emprunt
 *
 * @ORM\Table(name="emprunt")
 * @ORM\Entity(repositoryClass="DocumentBundle\Repository\EmpruntRepository")
 */
class Emprunt
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateemprunt", type="date")
     */
    private $dateemprunt;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dateretour", type="date")
     */
    private $dateretour;


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set dateemprunt
     *
     * @param \DateTime $dateemprunt
     *
     * @return Emprunt
     */
    public function setDateemprunt($dateemprunt)
    {
        $this->dateemprunt = $dateemprunt;

        return $this;
    }

    /**
     * Get dateemprunt
     *
     * @return \DateTime
     */
    public function getDateemprunt()
    {
        return $this->dateemprunt;
    }

    /**
     * Set dateretour
     *
     * @param \DateTime $dateretour
     *
     * @return Emprunt
     */
    public function setDateretour($dateretour)
    {
        $this->dateretour = $dateretour;

        return $this;
    }

    /**
     * Get dateretour
     *
     * @return \DateTime
     */
    public function getDateretour()
    {
        return $this->dateretour;
    }
    /**
     * @ORM\ManyToOne(targetEntity="document")
     *
     * @ORM\JoinColumn(name="documentid",referencedColumnName="id")
     */
    private $documentid;
    /**
     * @ORM\ManyToOne(targetEntity="eleve")
     *
     * @ORM\JoinColumn(name="eleveid",referencedColumnName="id")
     */
    private $eleveid;
}

